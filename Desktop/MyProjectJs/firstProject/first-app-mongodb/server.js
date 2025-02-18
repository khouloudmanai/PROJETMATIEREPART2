const express = require('express');
const mongoose = require('mongoose');
require('dotenv').config();
const app = express();
mongoose .connect(process.env.MONGO_URI)
.then(()=>{
    console.log('connected to server database')
})
.catch(err=>{
    console.log('Error connecting to server database')
})




const PORT = process.env.PORT || 3000
app.listen(PORT, ()=>{
    console.log('listening on port' +PORT);
})