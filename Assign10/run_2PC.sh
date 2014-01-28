teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config TwoPC_config.txt --compile
