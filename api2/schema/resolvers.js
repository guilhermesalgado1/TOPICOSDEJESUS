const Pessoa = require('../models/pessoa');

module.exports = {
  Query: {
    listarPessoas: async () => {
      console.log("📥 Chamada listarPessoas");
      const arr = await Pessoa.find();
      console.log("📦 listarPessoas →", arr);
      return arr;
    },

    // recebe args.id diretamente
    buscarPessoa: async ({ id }) => {
      console.log("📥 Chamada buscarPessoa com id =", id);
      const p = await Pessoa.findById(id);
      console.log("📦 buscarPessoa →", p);
      return p;
    },
  },

  Mutation: {
    // args = { nome, email }
    criarPessoa: async ({ nome, email }) => {
      console.log("📥 Chamada criarPessoa:", { nome, email });
      try {
        const nova = new Pessoa({ nome, email });
        const salva = await nova.save();
        console.log("✅ Pessoa criada →", salva);
        return salva;
      } catch (err) {
        console.error("❌ Erro criarPessoa:", err);
        return null;
      }
    },

    atualizarPessoa: async ({ id, nome, email }) => {
      console.log("📥 Chamada atualizarPessoa:", { id, nome, email });
      const upd = await Pessoa.findByIdAndUpdate(
        id,
        { nome, email },
        { new: true }
      );
      console.log("📦 atualizarPessoa →", upd);
      return upd;
    },

    deletarPessoa: async ({ id }) => {
      console.log("📥 Chamada deletarPessoa com id =", id);
      const pessoa = await Pessoa.findByIdAndDelete(id);
      console.log("📦 deletarPessoa →", pessoa);
      return !!pessoa;
    },
  },

  // mapeia campos do tipo Pessoa
  Pessoa: {
    id: parent => parent._id.toString(),
  },
};
