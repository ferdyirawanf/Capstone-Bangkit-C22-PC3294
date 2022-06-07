const functions = require("firebase-functions");
const express = require("express");
const cors = require("cors");

const admin = require("firebase-admin");
admin.initializeApp();
const db = admin.firestore();

const fruitApp = express();

fruitApp.use(cors({
    origin: true
}));

fruitApp.get("/", async (req, res) => {
    const snapshot = await db.collection("news").get();

    let news = [];
    snapshot.forEach((doc) => {
        let id = doc.id;
        let data = doc.data();

        news.push({
            id,
            ...data
        });
    });

    res.status(200).send(JSON.stringify(news));
});

fruitApp.get("/:id", async (req, res) => {
    const snapshot = await db.collection('news').doc(req.params.id).get();

    const fruitId = snapshot.id;
    const fruitData = snapshot.data();

    res.status(200).send(JSON.stringify({
        id: fruitId,
        ...fruitData
    }));
})

fruitApp.post("/", async (req, res) => {
    const fruit = req.body;

    await db.collection("news").add(fruit);

    res.status(201).send();
});

fruitApp.put("/:id", async (req, res) => {
    const body = req.body;

    await db.collection('news').doc(req.params.id).update(body);

    res.status(200).send()
});

fruitApp.delete("/:id", async (req, res) => {
    await db.collection("news").doc(req.params.id).delete();

    res.status(200).send();
})

exports.fruit = functions.https.onRequest(fruitApp);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.helloWorld = functions.https.onRequest((request, response) => {
    response.send("Hello from Google Cloud!");
});