select

{limit: first 5 offset 3}

distinct

/*
Just
a 
comment
*/
--*
-- a.b[6][7].id > "/x/y" (c.d),
-0x5AC4FED345A,
a.x > 234567.99e-3,
a.x = 0x5AC4FED345A, // 6237633459290
a.x != 07745277,
a.x <> "ASSDSD",
a.x <= "ASSDSD",
a.x >= "ASSDSD",
a.x < "ASSDSD",
- not abs A.f,
- a - not abs x,
a.b[6].id[7]@c > "/x/y" (c.d),
a[1:5],
a[b[5]:c[6]],
a[5],
a[b[c[5]]],
2007-10-14T21:35:44.563-0800,
false,
true,
FALSE,
TRUE,
False,
tRuE,
'a',
'B',
"asdbc\u002E",
'\u0065',
'\n',
'a'||'B'||'\u0065'||"def"||'\\',
"/f1\"/f2/func"(a,b,c) as f1,
count("/folder 1/folder 2/Customer".#order.*),
count(*),
count("/all ts/by cs".#all.#max.*),
"Home pets/dog".home.street@extId,
A@extId,
"/test concepts/Retail Customers/Customer".#order.#select.deliveryAddress.*,
A.b.c.*,
"/Channel/Functions"(x[3].y.z.c,"/cata/func"(),"/rule/function"()),
-- (String)(long)"/Datetime/now"(), not supported
"/CatA/CatB/abc"(B.id,C.name) ID,
"/f1/f2/func"(a,b,c) as f1,
A.x - F.g + B.y/C.s*M.n mod x,
A.x mod 5,
A.this,
A.name || B.name,
count(*),
count("/folder 1/folder 2/Customer".orders.*),
--count(B.*),
--count(select aa from bb),
--distinct ( select C.id from D ),
max ( E.id )
--avg ( select M.price from N )

from (select count(*) from "/ConceptE" where x>1) as sub,
     "/folderx/ConceptB" {policy: maintain where A > 100 last 30 purge first count(*) - 10 when count(*) - 1 < 1} as B,
	 "/ConceptC" {emit: new; policy: maintain by A, B, C where A > 123 last 30 minutes using timeProp purge first 1 when 15 < 1} as D,
	 "/Order/Line Item/ConceptA" {policy: maintain latest 3234; emit: dead} as A, 
	 "/ConceptD" as CD,
	 (select count(*) from "/ConceptE") as sub
	 --,(select count(*) from "/ConceptE")
--	 t in ( select a  from b )
where ( 
     A.code[7].id = "/Instance/getId"(B.someid)
	 and A.b*B.c*C.d = 20
	 and x = 0x34FA546ACDEF
	 and x = 0777563
	 and x = 123467898765544
	 and y = 123456
	 and x= 123e-123
	 and x= 123e+123
	 and x = 123.456e+25
	 and x = 123.456e-25
	 and x= 123E-123
	 and x= 123E+123
	 and x = 123.456E+25
	 and x = 123.456E-25
	 or ( - not abs A.f = -5 )
	 and AA.xx = 'A'
	 and not  F.x in A.customers
	 and ( A.price <= B.price
  			or A.price between 10 and 20
		 )
	)
	or  ( D@extId = "/Function/RuleFunction"(p1,p2,p3)
		    and ( 
		        B.id like "ABC%"
				or C.xyz between A.mid and B.nid 
				)
--		    and for all A in B.children
--		    or exists x in C.children
		    or a.b.x in ($1, 2.5, "abc")
		    or c.f.d in ($2+$3,"/x/y"(),"AAA")
		    or a.b.x in (select y from "/ConceptG")
		    or not (qqqq in (select blah from "/ConceptF"))
		    or exists (select y from "/ConceptG")
--		    and not ( exists x in C.children )
	)
--and m = (
--	select F.id from "/fl1/fl2/ConceptF" F
--)
group by {stateful_capture: all; emit: new} a,B.c,"/path/func"(a,b,c)
having a = 5
order by a.#max,b.#min,c[5] {limit: first 5 offset 8} ,"/x/y/f"(a)
;