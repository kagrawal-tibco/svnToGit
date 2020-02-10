select 
/*
Just
a 
comment
*/
--*

"/folder1/folder2/Customer.order.lineItems".*,
A.b.c.*,
"/Channel/Functions"(x[1][2].y.z.c,"/cata/func"(),"/rule/function"()),
-- (String)(long)"/Datetime/now"(), not supported
"/CatA/CatB/abc"(B.id,C.name) ID,
"/f1/f2/func"(a,b,c) as f1,
A.x - F.g + B.y/C.s*M.n mod x,
A.x mod 5,
A.this,
A.name || B.name,
count(*),
count("/folder1/folder2/Customer.order".*),
--count(B.*),
--count(select aa from bb),
--distinct ( select C.id from D ),
max ( E.id )
--avg ( select M.price from N )

from "/folderx/ConceptB", 
	 "/ConceptC",
	 "/Order/Line Item/ConceptA" A, 
	 "/ConceptD" as B
--	 t in ( select a  from b )
where ( 
     A.code[7].id = "/Instance/getId"(B.someid)
	 and A.b*B.c*C.d = 20
	 or ( - not abs A.f = -5 ) 
	 and AA.xx = 'WXYZ'	
	 and not  F.x in A.customers
	 and ( A.price <= B.price
  			or A.price between 10 and 20
		 )
	)
	or  ( D@extId = "/Function/RuleFunction"(p1,p2,p3)
		    and ( 
		        B.id like 'ABC%'
				or C.xyz between A.mid and B.nid 
				)
		    and for all A in B.children
		    or exists x in C.children
		    and not ( exists x in C.children )
	)
--and m = (
--	select F.id from "/fl1/fl2/ConceptF" F
--)
group by a,B.c,"/path/func"(a,b,c)
having a = 5
order by a.x,b.y,c[5],"/x/y/f"(a)
;