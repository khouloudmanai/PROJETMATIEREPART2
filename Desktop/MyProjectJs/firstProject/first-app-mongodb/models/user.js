const mongoos = require('mongoose')
const userSchema = new mongoos.Schema({
    name:String,
    email:{required:true,unique:true,type:String},
    age:Number
})

module.exports = mongoos.model('user', userSchema)