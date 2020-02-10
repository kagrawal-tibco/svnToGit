
import requests
from requests import Request, Session
from requests.auth import HTTPBasicAuth
import json, types, pprint, tempfile
import inspect, sys, pdb
import keyword, re
import jsonpickle #to encode and decode pojos
import imp
from types import ModuleType
from collections import OrderedDict
import tokenize
import token
import time

try:
    from urllib.parse import quote, unquote # for Python 3.4
    from io import StringIO
except ImportError:
    from urllib import quote, unquote # for Python 2.7
    from StringIO import StringIO

#global methods

#validate the operation or parameter name
#if identifier is not valid, return False, and if valid return True
def _operation_param_name_is_valid( name ):
    #python's definition for valid identifier is --> identifier ::=  (letter|"_") (letter | digit | "_")*
    #if name is a keyword in python
    if name in keyword.kwlist:
        return False
    else:
        if sys.version_info < (3, 0): #if python version is < than 3
            #python < 3 doesn't allow unicode characters in identifier
            identifier = re.compile(r"^[^\d\W]\w*\Z")
        else:
            #need to handle unicode characters in python >= 3, since allowed in python >= 3
            identifier = re.compile(r"^[^\d\W]\w*\Z", re.UNICODE)

        #check if the identifier matches the syntax or not. "None" output when invalid identifier
        result = re.match(identifier,name)
        if result != None: #return true when match found
            return True
        else: #return false when no match found
            return False

#finds the flaw in the operation or parameter name
#return the first invalid character found or return saying invalid identifier
def _find_flaw_in_name( name ):
    #if paramName is a reserver python keyword
    if name in keyword.kwlist:
        return 'matches a python keyword'
    else:
        #searches anything except letters, digits and underscore i.e [^a-zA-Z0-9_]
        if sys.version_info < (3, 0):
            identifier = re.compile(r"\W")
        else:
            #need to handle unicode characters in python >= 3
            identifier = re.compile(r"\W", re.UNICODE)
        
        #find the flaw in the name
        result = re.search(identifier, name)
        if result != None: #returns the first invalid character
            return 'contains an invalid character \''+result.group()+'\''
        else: #doesn't follow the identifier syntax
            return 'is an invalid python identifier'

#here all the invalid characters in python will be replaced by an underscore
def _cleanup_name( name ):
    #replace %xx escapes by their single-character equivalent.
    name = unquote(name)
    #replace each invalid character in python by '_' or if starting with number then prepend '_'
    if sys.version_info < (3, 0): #if python version is < than 3
        clean = lambda varStr: re.sub('\W|^(?=\d)','_', varStr)
    else:
        clean = lambda varStr: re.sub('\W|^(?=\d)','_', varStr, flags=re.UNICODE)
    return clean(name)

'''fixes the JsonString having comments as well. Needed when used with python 2.7
this is copied utility to clean up the json text, needed to handle python2 and python3 compatible json strings
needed to add, because json.loads handling'''
def _fix_lazy_json_with_comments (in_text):
    result = []
    tokengen = tokenize.generate_tokens(StringIO(in_text).readline)
    sline_comment = False
    mline_comment = False
    last_token = ''

    for tokid, tokval, _, _, _ in tokengen:
        # ignore single line and multi line comments
        if sline_comment:
            if (tokid == token.NEWLINE) or (tokid == tokenize.NL):
                sline_comment = False
            continue

        # ignore multi line comments
        if mline_comment:
            if (last_token == '*') and (tokval == '/'):
                mline_comment = False
            last_token = tokval
            continue

        # fix unquoted strings
        if (tokid == token.NAME):
            if tokval not in ['true', 'false', 'null', '-Infinity', 'Infinity', 'NaN']:
                tokid = token.STRING
                tokval = u'"%s"' % tokval

        # fix single-quoted strings
        elif (tokid == token.STRING):
            if tokval.startswith ("'"):
                tokval = u'"%s"' % tokval[1:-1].replace ('"', '\\"')

        # remove invalid commas
        elif (tokid == token.OP) and ((tokval == '}') or (tokval == ']')):
            if (len(result) > 0) and (result[-1][1] == ','):
                result.pop()

        # detect single-line comments
        elif tokval == "//":
            sline_comment = True
            continue

        # detect multiline comments
        elif (last_token == '/') and (tokval == '*'):
            result.pop() # remove previous token
            mline_comment = True
            continue

        result.append((tokid, tokval))
        last_token = tokval

    return tokenize.untokenize(result)

'''fixes the JsonString having comments as well. Needed when used with python 2.7
added this code since json.loads sorts the keys in map, and hence we lose the parameter order'''
def _json_decode (json_string, *args, **kwargs):
  try:
    return json.loads (json_string, object_pairs_hook=OrderedDict, *args, **kwargs)
  except:
    json_string = _fix_lazy_json_with_comments (json_string)
    return json.loads (json_string, object_pairs_hook=OrderedDict, *args, **kwargs)

#to get the value of a key from the map
def _get_value_of_key(key, actual_classnames):
    for k, value in actual_classnames.items():
        if key == value:
            return k

#to get the py/object name
def _get_py_object_name(classname, module_name, actual_classnames):
    return module_name+'.'+actual_classnames[_get_classname_from_id_attrib(classname)]

#to replace the $ref in schema with the respective schema
def _replace_ref_by_schema( schema, schema_type, ref, replacement ):
    schema['type'] = schema_type
    schema['id'] = ref
    schema['properties'] = replacement
    schema.pop('$ref', None)
    return schema

#to get the classname from the "id" attribute in jsonSchema
def _get_classname_from_id_attrib( id ):
    classname = id.replace("urn:jsonschema:", "")
    return classname.strip()

def _get_property_schema( schema, prop_schema ):
    #find the schema for top level pojo
    for key in schema.keys():
        if 'id' in schema.keys(): #when normal pojo
            prop_schema = schema
        elif schema.get('type') == 'array' and 'items' in schema.keys():
            prop_schema = _get_property_schema(schema.get('items'), prop_schema)
        elif 'additionalProperties' in schema.keys():
            prop_schema = _get_property_schema(schema.get('additionalProperties'), prop_schema)
        else:
            prop_schema = ''
    return prop_schema

#to get the classnames inside for each attribute in pojo class and properties for those classes
def _get_class_names( schema, classes, pojo_property_dict, pojo ):
    for key in schema.keys():
        try:
            if schema.get(key).get('type') == 'object' and 'id' in schema.get(key).keys(): #when normal pojo
                classname = _get_classname_from_id_attrib(schema.get(key).get('id'))
                if not classname in classes:
                    classes.append(classname)
                if "properties" in schema.get(key).keys(): #if nested properties, recurse down the line
                    pojo_property_dict[classname] = schema.get(key).get('properties')
                    pojo._prop_map[classname] = schema.get(key).get('properties')
                    _get_class_names(schema.get(key).get('properties'), classes, pojo_property_dict, pojo)
            elif schema.get(key).get('type') == 'array' and 'items' in schema.get(key).keys():
                _get_class_names(schema.get(key), classes, pojo_property_dict, pojo)
            elif 'additionalProperties' in schema.get(key).keys():
                 _get_class_names(schema.get(key), classes, pojo_property_dict, pojo)
        except:
            pass

#to evaluate the python type name for python object
def _get_python_type_name_for_object( type_name, enable_class_generation, schema ):
    prop_schema = None
    prop_schema = _get_property_schema( schema, prop_schema={} )
    #when the schema is not for a POJO, prop_schema will be ''(empty string)
    if not prop_schema:
        if schema.get('type') == 'array' and 'items' in schema.keys():
            return '<list '+_get_python_type_names(schema.get('items').get('type'), False, None)+'>'
        elif schema.get('type') == 'object' and 'additionalProperties' in schema.keys():
            return '<dict '+_get_python_type_names(schema.get('additionalProperties').get('type'), False, None)+'>'
        else:
            return 'dict'
    else:
        if schema.get('type') == 'object':
            if 'additionalProperties' in schema.keys(): #dict of pojo
                return '<dict '+_cleanup_name(_get_classname_from_id_attrib(prop_schema.get('id')))+'>'
            else: #normal pojo
                return '<'+_cleanup_name(_get_classname_from_id_attrib(prop_schema.get('id')))+'>'
        elif schema.get('type') == 'array': #list of pojos
            return '<list '+_cleanup_name(_get_classname_from_id_attrib(prop_schema.get('id')))+'>'

#maps the json type names to python type names
def _get_python_type_names( type_name, enable_class_generation, schema ):
    if type_name == 'string':
        return 'str'
    elif type_name == 'number':
        return 'int/float'
    elif type_name == 'object' and schema: #in case of internal tea objects schema will be empty
        return _get_python_type_name_for_object(type_name, enable_class_generation, schema)
    elif type_name == 'array':
        return 'list'
    elif type_name == 'boolean':
        return 'bool'
    elif type_name == None:
        return 'NONE'
    else:
        return type_name

#put py/object to the response, which will be used while deserializing the response to a python object
def _put_py_object( obj, rs, schema, result, module_name, actual_classnames ):
    for key in schema.keys():
        if schema.get(key).get('type') == 'object' and not 'additionalProperties' in schema.get(key).keys():
            if '$ref' in schema.get(key).keys():
                #replace $ref with it's schema
                schema[key] = _replace_ref_by_schema( schema[key], 'object', schema.get(key).get('$ref'), obj.module_._prop_map[_get_value_of_key(actual_classnames[_get_classname_from_id_attrib(schema.get(key).get('$ref'))], actual_classnames)] )
            result[key]['py/object'] = _get_py_object_name(schema.get(key).get('id'), module_name, actual_classnames)
            rs = _put_py_object( obj, rs, schema.get(key).get('properties'), result[key], module_name, actual_classnames)
        elif schema.get(key).get('type') == 'array' and not 'additionalProperties' in schema.get(key).keys() and _can_be_decoded( schema.get(key) ):
            if '$ref' in schema.get(key).get('items').keys():
                #replace $ref with it's schema
                schema[key]['items'] = _replace_ref_by_schema(schema.get(key).get('items'), 'object', schema.get(key).get('items').get('$ref'), obj.module_._prop_map[_get_value_of_key(actual_classnames[_get_classname_from_id_attrib(schema.get(key).get('items').get('$ref'))], actual_classnames)])
            #loop for all the values in the array
            i=0
            while i < (len(result[key])):
                result[key][i]['py/object'] = _get_py_object_name(schema.get(key).get('items').get('id'), module_name, actual_classnames)
                rs = _put_py_object( obj, rs, schema.get(key).get('items').get('properties'), result[key][i], module_name, actual_classnames)
                i = i+1
        elif schema.get(key).get('type') == 'object' and 'additionalProperties' in schema.get(key).keys() and _can_be_decoded( schema.get(key) ):
            if '$ref' in schema.get(key).get('additionalProperties').keys():
                #replace $ref with it's schema
                schema[key]['additionalProperties'] = _replace_ref_by_schema(schema.get(key).get('additionalProperties'), 'object', schema.get(key).get('additionalProperties').get('$ref'), obj.module_._prop_map[_get_value_of_key(actual_classnames[_get_classname_from_id_attrib(schema.get(key).get('additionalProperties').get('$ref'))], actual_classnames)])
            #for each key in the dict
            for k in result[key].keys():
                result[key][k]['py/object'] = _get_py_object_name(schema.get(key).get('additionalProperties').get('id'), module_name, actual_classnames)
                rs = _put_py_object( obj, rs, schema.get(key).get('additionalProperties').get('properties'), result[key][k], module_name, actual_classnames)
    return result

#to know if the schema is supported or not
def _can_be_decoded( schema ):
    if schema.get('type') == 'array' and 'additionalProperties' in schema.get('items').keys(): #List<Map>
        return False
    elif schema.get('type') == 'array' and 'items' in schema.get('items').keys(): #List<List>
        return False
    elif schema.get('type') == 'array' and not schema.get('items').get('type') == 'object':  # TEA-3314 : Handle List<nonPojo> case
        return False
    elif schema.get('type') == 'object' and 'additionalProperties' in schema.get('additionalProperties').keys(): #Map<Map>
        return False
    elif schema.get('type') == 'object' and 'type' in schema.get('additionalProperties').keys() and not schema.get('additionalProperties').get('type') == 'object':  # TEA-3314 : Handle Map<nonPojo> case
        return False
    elif schema.get('type') == 'object' and 'items' in schema.get('additionalProperties').keys(): #Map<List>
        #only map<list> is supported, else not supported i.e. #Map<List<Map>> or Map<List<List>> etc. are not supported
        if not ('additionalProperties' in schema.get('additionalProperties').get('items').keys() or 'items' in schema.get('additionalProperties').get('items').keys()):
            return True
        else:
            return False
    else:
        return True

#to handle response decoding for various scenarios
def _handle_pojo_response_decoding( result, schema, obj ):
    #when pojo schema generation system property is false, module_ will be None
    if obj.module_ != None:
        #retrieve the pojo classnames graph
        actual_classnames = obj.module_._classnames
        module_name = _cleanup_name(obj._object_id.split(':')[3]) + _cleanup_name(obj._object_id.split(':')[2])
        prop_schema = {}
        if result != None:
            if schema.get('type') == 'object' and not 'additionalProperties' in schema.keys():
                prop_schema = _get_property_schema(schema, prop_schema)
                if prop_schema:
                    rs={}
                    py_object_name = _get_py_object_name(prop_schema["id"], module_name, actual_classnames)
                    result = _put_py_object(obj, rs, prop_schema.get('properties'), result, module_name, actual_classnames)
                    result['py/object'] = py_object_name
                    #this is needed because the boolean value returned contains 'True' and jsonpickle understands 'true'
                    result = str(result).replace("'", '"').replace('True', 'true').replace('False', 'false')
                    result = jsonpickle.decode(_fix_lazy_json_with_comments(result).replace('u"', '"'))
            elif schema.get('type') == 'array' and not 'additionalProperties' in schema.keys() and _can_be_decoded( schema ):
                prop_schema = _get_property_schema(schema, prop_schema)
                if prop_schema:
                    i=0
                    while i < (len(result)):
                        rs={}
                        py_object_name = _get_py_object_name(prop_schema["id"], module_name, actual_classnames)
                        result[i] = _put_py_object(obj, rs, prop_schema.get('properties'), result[i], module_name, actual_classnames)
                        result[i]['py/object'] = py_object_name
                        #this is needed because the boolean value returned contains 'True' and jsonpickle understands 'true'
                        result[i] = str(result[i]).replace("'", '"').replace('True', 'true').replace('False', 'false')
                        result[i] = jsonpickle.decode(_fix_lazy_json_with_comments(result[i]).replace('u"', '"'))
                        i=i+1
            elif schema.get('type') == 'object' and 'additionalProperties' in schema.keys() and _can_be_decoded( schema ):
                if schema.get('additionalProperties').get('type') == 'object':
                    prop_schema = _get_property_schema(schema, prop_schema)
                    if prop_schema:
                        for key in result.keys():
                            rs={}
                            py_object_name = _get_py_object_name(prop_schema["id"], module_name, actual_classnames)
                            result[key] = _put_py_object(obj, rs, prop_schema.get('properties'), result[key], module_name, actual_classnames)
                            result[key]['py/object'] = py_object_name
                            #this is needed because the boolean value returned contains 'True' and jsonpickle understands 'true'
                            result[key] = str(result[key]).replace("'", '"').replace('True', 'true').replace('False', 'false')
                            result[key] = jsonpickle.decode(_fix_lazy_json_with_comments(result[key]).replace('u"', '"'))
                elif schema.get('additionalProperties').get('type') == 'array': #Map<List>
                    prop_schema = _get_property_schema(schema, prop_schema)
                    if prop_schema:
                        for key in result.keys():
                            i=0
                            while i < len(result[key]):
                                rs={}
                                py_object_name = _get_py_object_name(prop_schema["id"], module_name, actual_classnames)
                                result[key][i] = _put_py_object(obj, rs, prop_schema.get('properties'), result[key][i], module_name, actual_classnames)
                                result[key][i]['py/object'] = py_object_name
                                #this is needed because the boolean value returned contains 'True' and jsonpickle understands 'true'
                                result[key][i] = str(result[key][i]).replace("'", '"').replace('True', 'true').replace('False', 'false')
                                result[key][i] = jsonpickle.decode(_fix_lazy_json_with_comments(result[key][i]).replace('u"', '"'))
                                i=i+1
    return result

#to generate the pojo source code
def _generate_pojo_source_code(pojo, classes, pojo_property_dict):
    source = ''
    #generating the source code
    for cls in classes:
        if not 'class '+cls+'(object)' in pojo._pojoSource:
            source += \
                   "class %s(object):\n" % ( cls )
            source += "    def __init__(self, %s" % ', '.join(list(pojo_property_dict[cls].keys())) +"):\n" #defining __init__ for the class
            for prop in pojo_property_dict[cls]: #creating a variable in the __init__ method for each property in the property list
                source += "        self.%s = %s\n" % (prop, prop)
    return source

#handles the coversion of pojo params and return types to python classes
def _create_pojo( pojo, schema ):
    '''
        algorithm to pojo class code generation
        1. get the property schema till the attribute 'id' appears
        2. get the classname from the first 'id' attribute
        3. get the classnames in the rest of the schema
        4. get the properties as per classnames
    '''
    source = ''
    prop_schema = {}
    pojo_class_name = ''
    classes = []
    pojo_property_dict = {}

    if schema.get('type') == 'object' or schema.get('type') == 'array':
        prop_schema = _get_property_schema(schema, prop_schema)
        if prop_schema:
            pojo_class_name = _get_classname_from_id_attrib(prop_schema['id'])
            _get_class_names(prop_schema.get('properties'), classes, pojo_property_dict, pojo)
            if not pojo_class_name in classes:
                classes.append(pojo_class_name)
                pojo_property_dict[pojo_class_name] = prop_schema.get('properties')
                pojo._prop_map[pojo_class_name] = prop_schema.get('properties')
            _set_full_class_name_graph( pojo, classes )
            pojo._pojoSource += _generate_pojo_source_code(pojo, classes, pojo_property_dict)

#to set the fully classified classname graph
def _set_full_class_name_graph( pojo, classes ):
    #maintain the graph of fully classified classnames, needed in _clean_pojo_source function
    i=0
    while i < len(classes):
        if not classes[i] in pojo._full_class_names.values():
            pojo._full_class_names[classes[i]] = classes[i]
        i = i+1

#sets the stipped pojo class names and maintains the graph of pojo classnames, which will be used while desirializing
def _set_stripped_pojo_classnames( pojo, module ):
    class_names = {}
    for i, key in enumerate(pojo._full_class_names.keys()):
        # this split is needed since python doesn't allow characters like "<" (JAVA Generics)
        # in class names (fix for TEA-2976)
        removedGenericsPart = pojo._full_class_names[key].split('<')[0]
        class_names[key] = removedGenericsPart.split(':')[-1]
        i = i+1
    for key, value in class_names.items():
        flag = 0
        #storing the stripped classname, will be used while desirializing
        if len([(flag+1) for temp_value in class_names.values() if(value == temp_value)]) == 1:
            pojo._classnames[key] = value
        else:
            #need to clean the classname (replace ':' by '_'), for the case when full class name is required
            pojo._classnames[key] = _cleanup_name(key)

#this cleans the pojo source
def _clean_pojo_source( pojo, module ):
    _set_stripped_pojo_classnames( pojo, module )
    #now search if the stripped classname can stay in the module or not
    for key, value in pojo._classnames.items():
        pojo._pojoSource = pojo._pojoSource.replace('class ' + key + '(object)', 'class ' + value + '(object)')

#creates the module consisting pojos
def _create_module( source, name ):
    module = imp.new_module(name)
    compile( source, "<generated module>", "exec" )
    exec( source, module.__dict__)
    #adding module to sys.modules so that they can be found while deserializing
    sys.modules[name] = module
    return module

# returns true if the polling was successful and 
# returns false if the polling was unsuccessful after retries exhausts
def _poll( retry, wait, retry_action=lambda: None, test=lambda: True):
    for i in range(retry):
        try:
            print('INFO: Retry count: %s ' % (i+1))
            retry_action()
            if test():
                return True
        except:
            pass
        time.sleep(wait)
    return False

class _TeaServerConnection(object):
        
    def get( self, relUrl, params=None ):
        try:
            r = requests.get( self.serverUrl+relUrl, cookies=self.cookies, timeout=self.timeout, params=params, allow_redirects=False, cert=self.client_cert_path, verify=self.server_cert_path )
        except Exception:
            raise Exception( 'unable to connect to %s' % ( self.serverUrl ) )
        self._check_status_code(r)
        return r.json()

    def post( self, relUrl, params=None ):
        try:
            r = requests.post( self.serverUrl+relUrl, cookies=self.cookies, timeout=self.timeout, data=params, allow_redirects=False, cert=self.client_cert_path, verify=self.server_cert_path )
        except Exception:
            raise Exception( 'unable to connect to %s' % ( self.serverUrl ) )
        self._check_status_code(r)
        return r.json()

    def put_json( self, relUrl, body ):
        s = Session()
        req = Request('PUT', self.serverUrl+relUrl,
            data=json.dumps( body ),
            headers = { 'content-type' : 'application/json;charset=UTF-8' },
            cookies=self.cookies
        )
        prepped = req.prepare()
        r = s.send( prepped, timeout=self.timeout, allow_redirects=False, cert=self.client_cert_path, verify=self.server_cert_path )
        self._check_status_code(r)
        return r.json()

    def post_file( self, relUrl, fileValue ):
        r = requests.post( self.serverUrl+relUrl, files={ 'file': fileValue }, cookies=self.cookies, timeout=self.timeout, allow_redirects=False, cert=self.client_cert_path, verify=self.server_cert_path )
        self._check_status_code(r)
        return r.json()[ 0 ]

    def get_file( self, relUrl ):
        r = requests.get( self.serverUrl+relUrl, stream=True, cookies=self.cookies, timeout=self.timeout, allow_redirects=False, cert=self.client_cert_path, verify=self.server_cert_path )
        self._check_status_code(r)
        descr, path = tempfile.mkstemp()
        with open( path, 'wb' ) as fd:
            for chunk in r.iter_content():
                fd.write( chunk )
        return path

    def _login( self ):
        r = requests.post( self.serverUrl+'/tea/login.html', auth=HTTPBasicAuth(self.username, self.password), timeout=self.timeout, allow_redirects=False, cert=self.client_cert_path, verify=self.server_cert_path )
        self._check_status_code(r)
        return r.cookies

    #to check the response status code and raise the exception accordingly
    def _check_status_code( self, r ):
        if r.status_code == 303 and '/tea/login.html' in r.headers.get('location'):
            raise Exception( 'Invalid username or password or the session has expired, please re-login.' )
        elif r.status_code != 200:
            raise Exception( r.json()[ 'message' ] )

    def __init__( self, url, user='admin', pwd='admin', client_cert_path='', server_cert_path='', timeout=6 ):
        self.serverUrl = url
        self.username = user
        self.password = pwd
        self.timeout = timeout
        self.cookies = None
        self.client_cert_path = client_cert_path
        self.server_cert_path = server_cert_path

    def set_timeout( self, timeout=6 ):
        self.timeout = timeout

class _LazyReference(object) :

    def __init__( self, object_id, ref_name, delegate, *args, **kwargs ) :
        super( _LazyReference, self ).__init__(*args, **kwargs)
        self ._actual_dict = None
        self ._object_id = object_id
        self ._ref_name = ref_name
        self ._delegate = delegate
 
    def __nonzero__( self ) :
        return bool( self._data() )
 
    def __len__( self ) :
        return len( self._data() )
 
    def __setitem__( self, key, value ) :
        return self._data().__setitem__( key, value )
    
    def __getitem__( self, key ) :
        return self._data().__getitem__( key )
        
    def __delitem__( self, key ) :
        return self._data().__delitem__( key )
        
    def __contains__( self, key ):
        return self._data().__contains__( key )
 
    def keys( self ):
        return self._data().keys()
 
    def values( self ):
        return self._data().values()
 
    def items( self ):
        return self._data().items()
 
    def __str__( self ) :
        return self._data().__str__()
 
    def __repr__( self ) :
        return self._data().__repr__()
 
    def _data( self ) :
        if not self ._actual_dict:
            self .refresh_()
        return self ._actual_dict
        
    def refresh_( self ) :
        self ._actual_dict = self ._delegate ._get_reference( self ._object_id, self ._ref_name )
        return self

#this will hold the metadata for the pojo module, attached to every teaobject
class _PojoMetadataHolder( object ):
    def __init__( self, _pojoSource, _classnames, _full_class_names, _prop_map ):
        self._pojoSource = _pojoSource
        self._classnames = _classnames
        self._full_class_names = _full_class_names
        self._prop_map = _prop_map

class TeaObject( object ):

    # We want a minimum of methods on TeaObject, since they'll show up in help() on everything.
    #  This is why many methods that could be here are in EnterpriseAdministrator instead.

    def __init__( self, tea, obj_descr ):
        # record _object_id, to support operation invocation
        object_id = ''
        agentId = obj_descr[ 'agentId' ]
        if agentId != None:
            object_id = agentId
        key = obj_descr[ 'key' ]
        if key == None: # true for TEA members... defect?
            key = ''

        #need to encode the key for python < 3
        if sys.version_info < (3, 0):
            key = quote( key.encode('UTF-8'), safe='~()*!.\'' )  # rough equivalent to Javascript encodeURIComponent()
        else:
            key = quote( key, safe='~()*!.\'' )  # rough equivalent to Javascript encodeURIComponent()

        object_id = object_id + ":" + obj_descr[ 'type' ] + ":" + key
        self._object_id = object_id
        self.__name__ = obj_descr[ 'name' ]
        self._tea = tea
        tea._fetch_object_state( self, obj_descr )
        
    def refresh_( self ):
        obj_descr = self._tea._get_obj_descr( self._object_id )
        self._tea._fetch_object_state( self, obj_descr )
        return self
        
    def __str__( self ):
        return self.__repr__()

    def __repr__( self ):
        #need to cleanup name for unicode characters when python < 3
        self.__name__ = _cleanup_name(self.__name__)
        return '<\'' + self.__name__ + '\',\'' + self._object_id + '\'>'


class EnterpriseAdministrator(object):

    #find if the product API is provisional
    def product_with_provisional_api( self, name, retry=1, wait=0 ):
        if retry > 1:
            if _poll(retry=retry, wait=wait, retry_action=lambda: self.products.refresh_(), test=lambda: name in self._products_with_provisional_apis.keys()):
                return self._products_with_provisional_apis[name]
            else:
                raise Exception( 'Product \'%s\' not found in provisional products dictionary.' % ( name ) )
        else:
            if name in self._products_with_provisional_apis.keys():
                print('WARNING: The Python API for the \''+name+'\' product is provisional. It is not supported, and is likely to change in the future.')
                return self._products_with_provisional_apis[name]
            else:
                if name in self.products.keys():
                    print('The Python API for the product \''+name+'\' is supported, and you can access it from \'products\' dictionary on TIBCO Enterprise Administrator.')
                    return None
                else:
                    print('There is no product with name \''+name+'\' in TIBCO Enterprise Administrator.')
                    return None

    def product( self, name, retry=1, wait=0 ):
        if retry > 1:
            if _poll(retry=retry, wait=wait, retry_action=lambda: self.products.refresh_(), test=lambda: name in self.products.keys()):
                return self.products[name]
            else:
                raise Exception( 'Product \'%s\' not found in supported products dictionary.' % ( name ) )
        else:
            if name in self.products.keys():
                return self.products[name]
            else:
                if name in self._products_with_provisional_apis.keys():
                    print('WARNING: The Python API for the \''+name+'\' product is provisional. It is not supported, and is likely to change in the future.')
                    print('INFO: The Python API for the \''+name+'\' product can be accessed using EnterpriseAdministrator.product_with_provisional_api() method, by passing the name of the product.')
                    return None
                else:
                    print('There is no product with name \''+name+'\' in TIBCO Enterprise Administrator.')
                    return None

    #return the list of product names having provision API
    def products_with_provisional_apis( self ):
        print('WARNING: The Python APIs for the products '+str(list(self._products_with_provisional_apis.keys()))+' are provisional. They are not supported, and are likely to change in the future.')
        return list(self._products_with_provisional_apis.keys())

    def _get_obj_descr( self, object_id ):
        # TODO: more error handling
        return self._server.get( '/teas/obj', params = { 'id' : object_id } )

    def _fetch_object_state( self, obj, obj_descr ):
        obj.__setattr__( '_obj_descr', obj_descr )
        # record status field
        status = obj_descr[ 'status' ]
        if status:
            obj.__setattr__( 'status', status[ 'state' ] )
        # record config attributes as fields
        config = obj_descr[ 'config' ]
        if config:
            for key, value in config.items():
                obj.__setattr__( key, value )
        # record references as fields
        type_descr = obj.__class__.type_descr
        if type_descr and type_descr[ 'reference' ]:
            for ref_descr in type_descr[ 'reference' ]:
                ref_name = ref_descr[ 'name' ]
                if ref_descr[ 'multiplicity' ]:
                    # using a lazy dictionary, so we don't load the entire object graph at once!
                    obj.__setattr__( ref_name, _LazyReference( obj._object_id, ref_name, self ) )
                else:
                    # no laziness, no dictionary, just a singleton reference, so we can go ahead and fetch it.
                    # (Yes, we're assuming this won't cycle, or chain through thousands of singletons.)
                    singleton = self ._get_singleton( obj._object_id, ref_name )
                    if singleton: # handle optional singleton refs
                        obj.__setattr__( ref_name, singleton )

    def _invoke_operation( self, obj, op_descr, **kwargs ):
        params = {}
        argn = 0
        if op_descr['parameter'] != None:
            pDescsDict = dict( map( lambda d: (d['name'],d), op_descr[ 'parameter' ] ) )
            for paramName, val in kwargs.items():
                if paramName not in pDescsDict:
                    raise Exception( 'No parameter named "' + paramName + '" for operation ' + op_descr[ 'name' ] )
                paramDesc = pDescsDict[ paramName ]
                if paramDesc[ 'type' ] == 'file':
                    # Checking if file type parameter has a None value, if not pass the value further as it is. Added this check to fix TEA-3237.
                    if val!=None:
                        val = self._server.post_file( "/teas/fileupload", val )
                if paramDesc[ 'type' ] == 'reference':
                    if paramDesc[ 'multi-valued' ]:
                        val = [ x._object_id for x in val ]
                    else:
                        val = val._object_id
                if paramDesc[ 'type' ] == 'object' and self._config['enable_class_generation']:
                    if paramDesc[ 'multi-valued' ]:
                        #jsonpickle encode the val to json string
                        #_json_decode converts the encoded string to the ordered dict, to make it serializable on the server
                        val = [ _json_decode(jsonpickle.encode(each_val, unpicklable=False)) for each_val in val ]
                    else:
                        #jsonpickle encode the val to json string
                        #_json_decode converts the encoded string to the ordered dict, to make it serializable on the server
                        val = _json_decode(jsonpickle.encode(val, unpicklable=False))
                params[ paramName ] = val #assiging value to the parameter
        body = {}
        body[ 'operation' ] = op_descr[ 'name' ]
        body[ 'methodType' ] = op_descr[ 'method-type' ]
        body[ 'objectId' ] = obj._object_id
        body[ 'params' ] = params

        # TODO: more error handling
        result = self._server.put_json( "/teas/task", body )

        if isinstance( result, dict ):
            # This seems to be a defect.  The server is responding differently to TeaInternalAgent operations vs. RemoteAgent operations.
            result = result[ 'result' ]
        return_type = op_descr[ 'return-value' ]
        if return_type == 'reference':
            if isinstance( result, list ):
                result = [ self ._make_object( obj_descr ) for obj_descr in result ]
                if len( result ) == 1:
                    result = result[0]
            else:
                result = self ._make_object( result )
        elif return_type == 'file':
            # initial result is a resource path on the server
            result = self._server.get_file( "/tea/" + result )
        elif return_type == 'object' and self._config['enable_class_generation'] and op_descr[ 'return-schema' ]:
            #convert the string return-schema to dictinary while mainting the order of schema keys
            return_schema = _json_decode(op_descr[ 'return-schema' ])
            result = _handle_pojo_response_decoding( result, return_schema, obj )
        return result

    def _bad_operation( self, name, param_name, flaw='contains a space' ):
        errorMsg = "Operation \'%s\' is disabled because of an invalid TeaParam name.  Parameter \'%s\' %s." % ( name, param_name, flaw )
        #"*args" and "**kw" if user tries to invoke the abandoned operation with values
        # hence user will directly get the errorMsg instead of "TypeError" for number of arguments
        sourceStr =  "    def %s(self, *args, **kw):\n" % ( name )
        sourceStr += "        \"\"\"" + errorMsg + "\"\"\"\n"
        sourceStr += "        raise Exception( \"%s\" )\n" % ( errorMsg )
        return sourceStr

    def _make_operation( self, op_descr, op_index, omitted_operations, _pojo ):
        #need to encode "name" when python < 3
        if sys.version_info < (3, 0):
            name = str( op_descr['name'].encode('UTF-8') )
        else:
            name = str( op_descr['name'] )

        #omit the operation when the Operation name is an invalid python identifier or a python keyword
        if not _operation_param_name_is_valid(name):
            omitted_operations.append(name)
            return ''

        paramstr = kwparamstr = kwargstr = ""
        param_help=""

        if op_descr['parameter'] != None:
            for i, paramDesc in enumerate( op_descr['parameter'] ):
                param_default_val_help=""
                paramName = paramDesc[ 'name' ]
                paramType = paramDesc[ 'type' ]
                param_schema = None
                #call _bad_operation when the parameter name is an invalid identifier in python
                if not _operation_param_name_is_valid(paramName):
                    return self._bad_operation( name, paramName, flaw=_find_flaw_in_name(paramName))
                if paramName == 'agentId':
                    kwparamstr += ",agentId=None"
                    param_default_val_help = "(default None)"
                elif (paramType != 'reference') and (paramDesc[ 'default' ] != None):
                    if paramType == None: # accommodate bad metadata on older servers
                        paramType = 'string'
                    elif paramType == 'object' and self._config['enable_class_generation'] and paramDesc['schema']: # if paramType is an 'object' generate the class for that object and store it
                        #convert the string of schema to a dictionary and maintain the order of keys
                        param_schema = _json_decode(paramDesc['schema'])
                        _create_pojo( _pojo, param_schema )
                    multi_valued = paramDesc[ 'multi-valued' ]
                    default = str( paramDesc[ 'default' ] )
                    if default == "false":
                        default = "False"
                    elif default == "true":
                        default = "True"
                    kwparamstr += "," + paramName
                    if paramType == 'string' and not multi_valued:
                        kwparamstr += "='" + default + "'"
                    elif paramType == 'file' and not multi_valued:
                        kwparamstr += "='" + default + "'"
                    elif paramType == 'object':
                        kwparamstr += "=" + default
                    else:
                        kwparamstr += "=" + default
                    param_default_val_help = "(default "+default+")"
                elif paramDesc['type'] == 'object' and self._config['enable_class_generation'] and paramDesc['schema']: #when the paramType is an object and param 'default' is None
                    #convert the string of schema to a dictionary and maintain the order of keys
                    param_schema = _json_decode(paramDesc['schema'])
                    _create_pojo( _pojo, param_schema )
                    paramstr += "," + paramName
                else:
                    paramstr += "," + paramName
                param_type_help = "(type "+_get_python_type_names(paramType, self._config['enable_class_generation'], param_schema)+")"
                param_help += paramName+" -- "+paramDesc[ 'description' ]+" "+param_type_help+" "+param_default_val_help+"\n            "
                kwargstr += "," + paramName + "=" + paramName # _invoke_operation uses only kwargs

        return_schema = None
        #check if the returnType is already been pojoed or not, if not add it's class to the module
        if op_descr['return-value'] == 'object' and self._config['enable_class_generation'] and op_descr[ 'return-schema' ]:
            #make the return schema pojo and maintain the order of the keys
            return_schema = _json_decode(op_descr[ 'return-schema' ])
            _create_pojo( _pojo, return_schema )

        op_return_type_help = "No return value"
        if op_descr[ 'return-value' ] != None:
            op_return_type_help = 'Result Type: '+_get_python_type_names(op_descr[ 'return-value' ], self._config['enable_class_generation'], return_schema)

        #creating a python source for Tea operations
        sourceStr =  "    def %s(self%s%s):\n" % ( name, paramstr, kwparamstr )
        sourceStr +=  "        \"\"\"%s\n\n" % ( op_descr[ 'description' ] )
        sourceStr +=  "        Parameters:\n"
        sourceStr +=  "            %s\n" % ( param_help )
        sourceStr +=  "        %s\"\"\"\n" % ( op_return_type_help )
        sourceStr += "        op_descr = self.__class__.type_descr[ 'operation' ][ %s ]\n" % ( op_index )
        sourceStr += "        result = self._tea._invoke_operation(self,op_descr%s)\n" % ( kwargstr )
        if op_descr[ 'method-type' ] == 'UPDATE':
            sourceStr += "        self.refresh_()\n"
        sourceStr += "        return result\n"

        return sourceStr

    #this extracts the products which does not want to export APIs to python
    def _set_product_with_provisional_api( self, refDescr, productObj ):
        agent_type_name = refDescr['type'].split(':')[0]
        agent_type_version = refDescr['type'].split(':')[1]
        product_type_name = refDescr['name']
        agent_type = self._server.get( '/teas/agenttype/'+agent_type_name+':'+agent_type_version )
        if not 'exposePythonAPI' in agent_type.keys() or not agent_type[ 'exposePythonAPI' ]:
            self._products_with_provisional_apis[product_type_name] = productObj

    def _set_products_with_provisional_apis( self, object_id, name):
        self._products_with_provisional_apis = {} #to store products with provisional API
        refDescrs = self._server.post( '/teas/ref/' + object_id + '/' + name )
        for i in range(len(refDescrs)):
            refDescr = refDescrs[ i ]
            refObjName = refDescr[ 'name' ]
            refObj = self._make_object( refDescr )
            self._set_product_with_provisional_api(refDescr, refObj)

    # this is used in the _LazyReference for objects, and also initially for products
    def _get_reference( self, object_id, name ):
        refObjects = {}
        refDescrs = self._server.post( '/teas/ref/' + object_id + '/' + name )
        for i in range(len(refDescrs)):
            refDescr = refDescrs[ i ]
            refObjName = refDescr[ 'name' ]
            refObj = self._make_object( refDescr )
            # need to update the products dictionaries only when the object id '::::'
            if object_id == '::::':
                if not refObjName in self._products_with_provisional_apis.keys():
                    # when refresh on products i.e. tea.products.refresh_() is called, the provisional API should get distinguished from
                    # from the supported API
                    agent_type_name = refDescr['type'].split(':')[0]
                    agent_type_version = refDescr['type'].split(':')[1]
                    product_type_name = refDescr['name']
                    agent_type = self._server.get( '/teas/agenttype/'+agent_type_name+':'+agent_type_version )
                    if not 'exposePythonAPI' in agent_type.keys() or not agent_type[ 'exposePythonAPI' ]:
                        self._products_with_provisional_apis[product_type_name] = refObj
                    else:
                        refObjects[ refObjName ] = refObj
            else:
                refObjects[ refObjName ] = refObj
        return refObjects

    def _get_singleton( self, object_id, name ):
        obj = self._server.post( '/teas/ref/' + object_id + '/' + name )
        if isinstance( obj, list ):
            if len( obj ) == 0:
                return None
                #raise Exception( 'Missing multiplicity on reference "' + name + '" of ' + object_id )
            obj = obj[ 0 ]
        return self._make_object( obj )

    # lookup or create a Python class for a TEA objectType
    def _get_object_type( self, objectTypeName, module_name ):
        #generates the classname by replacing each invalid character by "_"
        className = _cleanup_name(objectTypeName)
        if className in self._object_types:
            return self._object_types[ className ]
        #pojoMetadataHolder object to hold pojo module metadata
        _pojo = _PojoMetadataHolder( _pojoSource='', _classnames={}, _full_class_names={}, _prop_map={})
        tea = self  # so we can use it inside the new methods
        typeIds = { 'id' : [ objectTypeName ] }
        type_descr = self._server.post( '/teas/types', params=json.dumps( typeIds ) )[ 0 ]

        source = \
            "class %s (TeaObject):\n" % ( className )
        if type_descr and type_descr[ 'description' ]:
            source += "    \"\"\"%s\"\"\"\n" % (type_descr[ 'description' ])
        source += "    omitted_operations = omitted_operations\n"
        source += "    type_descr = global_type_descr\n"
        source += "    module_ = pojo_module\n"

        omitted_operations = []
        #if the type_descr is not None
        if type_descr and type_descr[ 'operation' ]:
            # append the source for each TEA operation
            op_descrs = type_descr[ 'operation' ]
            for i in range(len(op_descrs)):
                op_descr = op_descrs[ i ]
                internal = op_descr[ 'internal' ]
                # if Tea Server is < 2.0.0 i.e. 1.3.0, hideFromClients won't be available hence considering it same as hide from None
                if not internal:
                    if 'hideFromClients' in op_descr.keys():
                        if op_descr[ 'hideFromClients' ] != None:
                            if not "ANY" in op_descr[ 'hideFromClients' ] and not "PYTHON" in op_descr[ 'hideFromClients' ]:
                                source += self._make_operation( op_descr, i, omitted_operations, _pojo )
                    else:
                        source += self._make_operation( op_descr, i, omitted_operations, _pojo )

        pojo_module=None
        if _pojo._pojoSource != "":
            #pojo_module = _create_module(_pojo._pojoSource, module_name)
            _clean_pojo_source(_pojo, pojo_module) #to clean the pojo source code
            pojo_module = _create_module(_pojo._pojoSource, module_name) #recreate the modules after cleaning the pojo source code
            pojo_module._source = _pojo._pojoSource
            pojo_module._classnames = _pojo._classnames
            pojo_module._prop_map = _pojo._prop_map

        class_code = compile( source, "<generated class>", "exec" )
        fakeglobals = {}
        eval( class_code, { "TeaObject": TeaObject, "global_type_descr": type_descr, "omitted_operations": omitted_operations, "pojo_module": pojo_module }, fakeglobals )
        result = fakeglobals[ className ]
        result ._source = source

        # cache the objectType (class) so we don't have to create it again
        self._object_types[ className ] = result
        return result

    def _make_object( self, obj_descr ):
        module_name = ''
        #naming the modules
        if not 'tea' in obj_descr['type']:
            module_name = _cleanup_name(obj_descr['type'].split(':')[2]) + _cleanup_name(obj_descr['type'].split(':')[1])
        objectType = self._get_object_type( obj_descr[ 'type' ], module_name )
        result = objectType( self, obj_descr )  # calls the constructor
        # pprint.pprint( result )
        return result

    def __init__( self, url='useArgv', user='admin', pwd='admin', client_cert_path='', server_cert_path=None, timeout=6, config={'enable_class_generation':False}, retry=1, wait=0 ):
        if url == 'useArgv':
            url = 'http://localhost:8777'
            if len( sys.argv ) > 1:
                url = sys.argv[ 1 ]
        # empty string check was needed to fix TEA-3015, please see the details of the fix in JIRA ticket
        if server_cert_path == '':
            sys.exit("ERROR: Valid values for 'server_cert_path' parameter is [False or a valid certificate path].")
        self._server = _TeaServerConnection( url, user, pwd, client_cert_path, server_cert_path, timeout )
        self._config = config
        if retry > 1:
            # poll to see if the TEA Server URL is up
            # TEA-3012 - when the server is SSL enabled, we need to pass the client and server certs in the retry_action in the _poll method
            # TEA-3023 - passed _retry_init method as the retry lambda i.e. test for __poll. For more details please see the description on method _retry_init
            if not _poll(retry=retry, wait=wait, test=lambda: self._retry_init()==True):
                raise Exception( 'Unable to connect to %s url.' % (url) )
            else:
                self.login()
                self.refresh_()
        else:
            self.login()
            self.refresh_()

    '''
        retry_init does the initialzation part.
        This method returns True if all the initialization steps are successful with no exception else ignores the exception occured and returns False
        This method is used only when the "retry" and "wait" parameters are present while initialising EnterpriseAdministrator.
    '''
    def _retry_init( self ):
        try:
            self.login()
            self.refresh_()
            return True
        except Exception as e:
            print("\nWARN: Exception while logging in. If the credentials are correct, this may mean that the server is not yet ready to serve authentication.")
            print("ERROR: Actual error while retrying is: %s.\n" % str(e))
            return False

    def refresh_( self ):
        self._object_types = {}
        #this will retrieve all the products with provisional API
        self._set_products_with_provisional_apis( '::::', 'members' )
        teaProduct = self._products_with_provisional_apis[ 'TEA' ]
        for refName, val in teaProduct.__dict__.items():
            self .__setattr__( refName, val )
        # the lazy ref will omit TEA, so that refresh_ always does
        self.products = _LazyReference( '::::', 'members', self )
        # TODO also take on any operations from the TEA product object
        return self

    def createOperatorView( self, name, objects, descr='' ):
        ovm = self.members[ 'OperatorViewManager' ]
        op_descrs = ovm.__class__.type_descr[ 'operation' ]
        for i in range(len(op_descrs)):
            op_descr = op_descrs[ i ]
            if op_descr[ 'name' ] == 'addOperatorView':
                self._tea._invoke_operation( ovm, op_descr, name=name, description=descr, objectidentifiers=objects )
                return ovm.refresh_().members[ name ]
        raise Exception( 'did not find addOperatorView operation' )

    def timeout( self, timeout ):
        return _TeaTimeoutContext( self._server, timeout )

    def login( self ):
        self._server.cookies = self._server._login()

class _TeaTimeoutContext(object):

    def __init__( self, server, timeout ):
        self.server = server
        self.timeout = timeout

    def __enter__( self ):
        self .server .set_timeout( self .timeout )

    def __exit__( self, type, value, traceback ):
        self .server .set_timeout()

