'use client';
import { gql, useLazyQuery } from '@apollo/client';

const GET_PESSOAS = gql`
  query GetPessoas {
    listarPessoas {
      id
      nome
      email
    }
  }
`;

export default function PessoasButton() {
  const [fetch, { loading, data, error }] = useLazyQuery(GET_PESSOAS, {
    fetchPolicy: 'network-only',
    notifyOnNetworkStatusChange: true,
    skip: true,          // só dispara quando clicar
  });

  return (
    <>
      <button
        onClick={() => fetch()}
        className="mt-4 px-4 py-2 bg-purple-600 text-white rounded"
      >
        Listar pessoas (GraphQL)
      </button>

      {loading && <p>Carregando…</p>}
      {error   && <p className="text-red-500">{error.message}</p>}

      <ul className="mt-2 list-disc pl-6">
        {data?.listarPessoas.map(p => (
          <li key={p.id}>
            {p.nome || '(sem nome)'} — {p.email || '(sem e-mail)'}
          </li>
        ))}
      </ul>
    </>
  );
}
