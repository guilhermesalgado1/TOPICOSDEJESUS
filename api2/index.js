const express = require('express');
const mongoose = require('mongoose');
const { graphqlHTTP } = require('express-graphql');
const { buildSchema } = require('graphql');
const fs = require('fs');
const path = require('path');

// importe seu objeto de resolvers
const resolvers = require('./schema/resolvers');
const { Query, Mutation /*, Pessoa: PessoaFields se quiser mapear campos */ } = resolvers;

const app = express();
const PORT = 4000;

// Conexão com o MongoDB
mongoose.connect(
  'mongodb+srv://samuelmoro:44IXUEUnyJPei7o8@cluster0.irlyj92.mongodb.net/?retryWrites=true&w=majority',
  {
    dbName: 'test',
  }
)
.then(() => console.log('✅ Conectado ao MongoDB com sucesso!'))
.catch(err => console.error('❌ Erro ao conectar ao MongoDB:', err));

// Rota de teste
app.get('/', (req, res) => res.send('Servidor está funcionando!'));

// Schema GraphQL
const schemaString = fs.readFileSync(path.join(__dirname, 'schema.graphql'), 'utf8');
const schema = buildSchema(schemaString);

// **Flatten**: espalha Query e Mutation no rootValue
app.use(
  '/graphql',
  graphqlHTTP({
    schema,
    rootValue: {
      ...Query,
      ...Mutation,
      // se quiser mapear campos de tipo (ex: id), descomente:
      // id: resolvers.Pessoa.id,
    },
    graphiql: true,
  })
);

// Inicia o servidor
app.listen(PORT, () => {
  console.log(`🚀 Servidor rodando em http://localhost:${PORT}`);
  console.log(`🌐 Acesse GraphQL em http://localhost:${PORT}/graphql`);
});
