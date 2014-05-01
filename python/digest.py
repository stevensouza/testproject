from elasticsearch import Elasticsearch
import hashlib

ret = {}
ret['rrname'] = "myname"
ret['rrtype'] = "mytype"
 
m = hashlib.sha256()
m.update(str(ret))
print(m.hexdigest())

print(ret)

es = Elasticsearch([
    {'host': '10.102.50.250', 'port': 9200}
])

#doc = {
#    'rrname': 'stevesouza.com',
#    'rrtype': 'A'
#}


#res = es.index(index="dipits-dns", doc_type='testdns', id="MD5", body=doc)
#print(res['ok'])
#print(res)

res = es.get(index="dipits-dns", doc_type='dns', id="e849ddd145a8241a76b176fa9069bc84")
print("*****full object printed")
print(res)
print("***just source")
print(res['_source'])
print("****string version")
print(str(res))
m = hashlib.sha256()
m.update(str(res))
print("***sha256 hex: ",m.hexdigest())
print(m.hexdigest())


