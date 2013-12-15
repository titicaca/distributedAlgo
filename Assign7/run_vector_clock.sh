teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config vector_clock_config.txt --compile

