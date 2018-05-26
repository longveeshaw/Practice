package com.practice.lucene.group;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneGroupDemo {

    public static void main(String[] args) {
        //indexWriter
        //  analyzer, indexWriterConfig, document
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        Directory directory = null;
        /*try {
            directory = FSDirectory.open(Paths.get("/tmp/lucene-index"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        directory = new RAMDirectory();

        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(directory, config);

            createIndexForGroup(indexWriter, 1, "Java", "一周精通Java");
            createIndexForGroup(indexWriter, 2, "Java", "一周精通MyBatis");
            createIndexForGroup(indexWriter, 3, "Java", "一周精通Struts");
            createIndexForGroup(indexWriter, 4, "Java", "一周精通Spring");
            createIndexForGroup(indexWriter, 5, "Java", "一周精通Spring Cloud");
            createIndexForGroup(indexWriter, 6, "Java", "一周精通Hibernate");
            createIndexForGroup(indexWriter, 7, "Java", "一周精通JVM");
            createIndexForGroup(indexWriter, 8, "C", "一周精通C");
            createIndexForGroup(indexWriter, 9, "C", "C语言详解");
            createIndexForGroup(indexWriter, 10, "C", "C语言调优");
            createIndexForGroup(indexWriter, 11, "C++", "一周精通C++");
            createIndexForGroup(indexWriter, 12, "C++", "C++语言详解");
            createIndexForGroup(indexWriter, 13, "C++", "C++语言调优");
            indexWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


        //===========

        try {
            //indexSearcher
            IndexSearcher indexSearcher = new IndexSearcher(DirectoryReader.open(directory));

            QueryParser queryParser = new QueryParser("content", new StandardAnalyzer());
            Query query = queryParser.parse("精通");

          /*  TopDocs topDocs = indexSearcher.search(query, 10);

            System.out.println("topDocs.totalHits = " + topDocs.totalHits);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                System.out.println("doc = " + doc);
            }*/

            // group search
            System.out.println("======== group search =================");
            GroupingSearch groupingSearch = new GroupingSearch("author");
            groupingSearch.setGroupSort(Sort.RELEVANCE);
            groupingSearch.setFillSortFields(true);
            groupingSearch.setCachingInMB(4.0, true);
            groupingSearch.setAllGroups(true);
            groupingSearch.setGroupDocsLimit(10);
            groupingSearch.setGroupDocsOffset(0);

            TopGroups<BytesRef> topGroups = groupingSearch.search(indexSearcher, query, 0, 10);
            Integer totalGroupCount = topGroups.totalGroupCount;
            System.out.println("totalGroupCount = " + totalGroupCount);

            GroupDocs<BytesRef>[] groups = topGroups.groups;
            for (GroupDocs<BytesRef> group : groups) {
                System.out.println("group.groupValue = " + group.groupValue.utf8ToString());
                System.out.println("group.totalHits = " + group.totalHits);
                ScoreDoc[] scoreDocs = group.scoreDocs;
                for (ScoreDoc scoreDoc : scoreDocs) {
                    Document doc = indexSearcher.doc(scoreDoc.doc);
                    System.out.println("doc = " + doc);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createIndexForGroup(IndexWriter indexWriter, int id, String author, String content) throws IOException {
        Document document = new Document();

        document.add(new Field("id", id + "", TextField.TYPE_STORED));
        document.add(new Field("author", author, TextField.TYPE_STORED));
        document.add(new SortedDocValuesField("author",new BytesRef(author)));
        document.add(new Field("content", content, TextField.TYPE_STORED));
        indexWriter.addDocument(document);
        indexWriter.commit();

    }


}
