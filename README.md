neo4j-test
==========


MATCH (n)
OPTIONAL MATCH (n)-[r]-()
DELETE n, r



CREATE (o1:Org { name:'Org1' }),(o2:Org { name:'Org2' }),(o3:Org { name:'Org3' }),(o4:Org { name:'Org4' }),(o5:Org { name:'Org5' }),(o6:Org { name:'Org6' }),(o7:Org { name:'Org7' }),(o1)-[:PARENT_OF]->(o2),(o1)-[:PARENT_OF]->(o3),(o3)-[:PARENT_OF]->(o4),(o3)-[:PARENT_OF]->(o5),(o4)-[:PARENT_OF]->(o6),(o4)-[:PARENT_OF]->(o7),(o2)-[:LOGIN]->(o4),(o7)-[:LOGIN]->(o5),(o2)-[:CONTACT_SHARING]->(o7),(o6)-[:SALE_SHARING]->(o2),(o6)-[:CONTACT_SHARING]->(o7),(o7)-[:CONTACT_SHARING]->(o6)
