Populate Database
=================

Replace $(docker-machine ip) with your container IP:

    $ mongorestore -h $(docker-machine ip) -d mmaps mmaps

Simple queries
==============

    $ mongo
    > show dbs
    > use mmaps
    > show collections
    > db.office.find().count()
    > db.office.findOne()
    > db.office.findOne().address
    > db.office.findOne().loc
    > db.office.findOne().address.loc[0]

Spatial queries
===============

Offices inside a 10-km radius from location (0.8, 41.78):

    db.office.find({"address.loc":{$nearSphere:{$geometry:{type:"Point",coordinates:[0.8, 41.78]}},$maxDistance:10000}})
	
Offices with local promos:

    db.office.find({"promos":{$elemMatch:{"type":"LOCAL"}}})
	
Offices inside a 100-km radius from location (0.8, 41.78) with local promos:

    db.office.find({"address.loc":{$nearSphere:{$geometry:{type:"Point",coordinates:[0.8, 41.78]}},$maxDistance:100000}, "promos":{$elemMatch:{"type":"LOCAL"}}})
	
Office count inside a 10-km radius from Madrid:	

    db.office.find({"address.loc":{$nearSphere:{$geometry:{type:"Point",coordinates:db.city.findOne({"name":"Madrid"}).loc}},$maxDistance:10000}}).count()

