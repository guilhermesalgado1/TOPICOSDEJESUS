// src/services/apiPessoas.js
import { gql } from '@apollo/client';
import { apolloClient } from './apolloClient';

// --- QUERIES ----------------------------------------------------------------
export const QUERY_LISTAR_PESSOAS = gql`
  query ListarPessoas {
    listarPessoas {
      id
      nome
      email
    }
  }
`;

export const QUERY_BUSCAR_PESSOA = gql`
  query BuscarPessoa($id: ID!) {
    buscarPessoa(id: $id) {
      id
      nome
      email
    }
  }
`;

// --- MUTATIONS --------------------------------------------------------------
export const MUTATION_CRIAR_PESSOA = gql`
  mutation CriarPessoa($nome: String!, $email: String!) {
    criarPessoa(nome: $nome, email: $email) {
      id
      nome
      email
    }
  }
`;

export const MUTATION_ATUALIZAR_PESSOA = gql`
  mutation AtualizarPessoa($id: ID!, $nome: String!, $email: String!) {
    atualizarPessoa(id: $id, nome: $nome, email: $email) {
      id
      nome
      email
    }
  }
`;

export const MUTATION_DELETAR_PESSOA = gql`
  mutation DeletarPessoa($id: ID!) {
    deletarPessoa(id: $id)
  }
`;

// helpers (retornam Promises — igual aos serviços REST)
export const listarPessoas   = () => apolloClient.query({ query: QUERY_LISTAR_PESSOAS });
export const buscarPessoa    = id  => apolloClient.query({ query: QUERY_BUSCAR_PESSOA, variables: { id }});
export const criarPessoa     = ({ nome, email }) =>
  apolloClient.mutate({ mutation: MUTATION_CRIAR_PESSOA, variables: { nome, email }});

export const atualizarPessoa = ({ id, nome, email }) =>
  apolloClient.mutate({ mutation: MUTATION_ATUALIZAR_PESSOA, variables: { id, nome, email }});

export const deletarPessoa   = id =>
  apolloClient.mutate({ mutation: MUTATION_DELETAR_PESSOA, variables: { id }});
