const mongoose = require('mongoose');
const PessoaSchema = new mongoose.Schema({
  nome: String,
  email: String,
});
module.exports = mongoose.model('Pessoa', PessoaSchema);
