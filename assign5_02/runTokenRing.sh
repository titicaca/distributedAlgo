teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config token_ring_config.txt --compile

