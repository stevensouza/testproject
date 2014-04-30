from datetime import datetime
from elasticsearch import Elasticsearch

es = Elasticsearch([
    {'host': '10.102.50.250', 'port': 9200}
])

doc = {
    'rrname': 'stevesouza.com',
    'rrtype': 'A'
}


res = es.index(index="dipits-dns", doc_type='testdns', id="MD5", body=doc)
print(res['ok'])
print(res)

# res = es.get(index="dipits", doc_type='whois', id="Cs0Xd6DHQIKFtl6zzoh83g")
# print(res['_source'])

