from elasticsearch import Elasticsearch
import hashlib


es = Elasticsearch([
    {'host': '10.102.50.250', 'port': 9200}
])


res = es.get(index="dipits-dns", doc_type='dns', id="e849ddd145a8241a76b176fa9069bc84")


print("***just source")
print(res['_source'])
print("****string version")
print(res['_source'])
m = hashlib.sha256()
m.update(str(res['_source']))
print("***sha256 hex: ",m.hexdigest())

file = open("migrate.out", "w")
file.write(m.hexdigest())

file.close()

with open ("migrate.out", "r") as myfile:
    data=myfile.read()
    
print data    


