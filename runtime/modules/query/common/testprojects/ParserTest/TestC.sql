select

count(*)

from (select count(*) from "/ConceptE" where x>1) as sub
where
     A.code[7].id = "/Instance/getId"(B.someid)
     and x = a union b

group by {stateful_capture: all; emit: new} a,B.c,"/path/func"(a,b,c)
having a = 5
order by a.#max
union
( select

count(*)

from (select count(*) from "/ConceptE" where x>1) as sub
where (
     A.code[7].id = "/Instance/getId"(B.someid)
	)
group by {stateful_capture: all; emit: new} a,B.c,"/path/func"(a,b,c)
having a = 5
order by a.#max )
;