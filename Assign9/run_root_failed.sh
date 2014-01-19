teachnetpath=$(head -1 teachnet.path)
java -jar $teachnetpath --cp . --config ssspan_tree_root_failed.txt --compile
