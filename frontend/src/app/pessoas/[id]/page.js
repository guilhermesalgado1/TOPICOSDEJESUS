'use client';
import { useEffect, useState } from 'react';
import { buscarPessoa, atualizarPessoa, deletarPessoa } from '@/services/apiPessoas';
import { useRouter } from 'next/navigation';

export default function PessoaDetalhe({ params }) {
  const id      = params.id;
  const router  = useRouter();
  const [nome, setNome]   = useState('');
  const [email, setEmail] = useState('');
  const [msg, setMsg]     = useState('');

  useEffect(() => {
    buscarPessoa(id)
      .then(r => {
        const p = r.data?.buscarPessoa;
        if (p) {
          setNome(p.nome);
          setEmail(p.email);
        }
      })
      .catch(() => setMsg('Erro ao carregar'));
  }, [id]);

  const salvar = () => {
    atualizarPessoa({ id, nome, email })
      .then(() => setMsg('Salvo!'))
      .catch(() => setMsg('Erro ao salvar'));
  };

  const remover = () => {
    if (confirm('Deseja realmente excluir?'))
      deletarPessoa(id).then(() => router.push('/pessoas'));
  };

  return (
    <div className="p-6 max-w-md space-y-4">
      <h1 className="text-xl font-bold">Editar Pessoa #{id}</h1>

      <label className="block text-sm">Nome
        <input value={nome} onChange={e=>setNome(e.target.value)}
               className="w-full mt-1 rounded bg-gray-800 p-2" />
      </label>

      <label className="block text-sm">E-mail
        <input value={email} onChange={e=>setEmail(e.target.value)}
               className="w-full mt-1 rounded bg-gray-800 p-2" />
      </label>

      <div className="flex gap-2">
        <button onClick={salvar}
          className="rounded bg-green-600 px-3 py-1 hover:bg-green-700">Salvar</button>

        <button onClick={remover}
          className="rounded bg-red-600 px-3 py-1 hover:bg-red-700">Excluir</button>
      </div>

      {msg && <p className="text-xs text-gray-400">{msg}</p>}
    </div>
  );
}
