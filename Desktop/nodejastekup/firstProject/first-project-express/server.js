const express = require('express');
const app = express();
app.get('/', (req,res)=>{ 
    res.send({message:"hello from my server"})//can return all types of data in reponse
res.json({message:"hello"}) //accept response only json
res.end({message:"do this and skip others requests"})
res.statusCode(404).send({message:"product not found"})
})
app.get('/file',(req,res)=>{
    res.sendFile(__dirname + '/public/index.html')
})

app.listen(4000,()=>

    
console.log('Listening on port '+ 4000));