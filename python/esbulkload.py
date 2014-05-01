from datetime import datetime
from elasticsearch import Elasticsearch

es = Elasticsearch([
    {'host': '10.102.50.250', 'port': 9200}
])

doc = [
{ "index" : { "_index" : "ipits-dns", "_type" : "testdns", "_id" : "333" } },
{ "rrname": "www.stevesouza.com", "rrtype": "A" }
]


res = es.bulk(index="dipits-dns", doc_type='testdns',  body=doc)
print(res)

# res = es.get(index="dipits", doc_type='whois', id="Cs0Xd6DHQIKFtl6zzoh83g")
# print(res['_source'])

