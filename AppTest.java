/*---------------------------------------------------------------------------------------------
 *  Copyright (c) Red Hat, Inc. All rights reserved.
 *  Licensed under the MIT License. See LICENSE in the project root for license information.
 *--------------------------------------------------------------------------------------------*/
package com.codenvy.example.java;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Unit test for simple App.
 */
@RunWith(JUnit4.class)
public class AppTest {

    protected MongoDatabase db;
    protected MongoDatabase testDb;
    protected MongoCollection<Document> billingRecordsCollection;

    @Test
    public void testApp() {

        String mongoUri = "mongodb+srv://offshoreuser:pAaqwMRsvZwPIfef@cluster0.ymjan.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
        String databaseName = "albert_test";
        db = MongoClients.create(mongoUri).getDatabase(databaseName);
        billingRecordsCollection = db.getCollection("BillingRecords");
        //testDb = MongoClients.create(mongoUri).getDatabase("testDb");

        Document queryFilter = new Document("billingRecordID", (int) 10000001);
        List<Document> results = new ArrayList<>();

        Date firstDate = new Date();
        billingRecordsCollection.find(queryFilter).into(results);
        Date secondDate = new Date();

        if(results.size() > 0) {
            System.out.println(results.get(0).toJson());
        } else {
            System.out.println("no results");
        }
        int timeout = 10000;
        Date beforeTime = new Date();
        try{
            boolean reachable =  InetAddress.getByName("cluster0-shard-00-00.ymjan.mongodb.net").isReachable(timeout);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        Date afterTime = new Date();
        System.out.println("running time(ms): " + Math.abs(secondDate.getTime() - firstDate.getTime()));
        System.out.println("latecy(ms): " + Math.abs(afterTime.getTime() - beforeTime.getTime()));
        assertTrue(true);
    }
}
