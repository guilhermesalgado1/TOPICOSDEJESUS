'use client';
import { useState } from 'react';
import { criarPessoa } from '@/services/apiPessoas';
import { useRouter } from 'next/navigation';

export default function NovaPessoa() {
  const router        = useRouter();
  const [nome,setNome]= useState('');
  const [email,setEmail]=useState('');
  const [msg,setMsg]  = useState('');

  const salvar = () => {
    criarPessoa({ nome, email })
      .then(() => router.push('/pessoas'))
      .catch(()  => setMsg('Erro ao salvar'));
  };

  return (
    <div className="p-6 max-w-md space-y-4">
      <h1 className="text-xl font-bold">Nova Pessoa</h1>

      <label className="block text-sm">Nome
        <input value={nome} onChange={e=>setNome(e.target.value)}
               className="w-full mt-1 rounded bg-gray-800 p-2" />
      </label>

      <label className="block text-sm">E-mail
        <input value={email} onChange={e=>setEmail(e.target.value)}
               className="w-full mt-1 rounded bg-gray-800 p-2" />
      </label>

      <button onClick={salvar}
        className="rounded bg-blue-600 px-3 py-1 hover:bg-blue-700">Salvar</button>

      {msg && <p className="text-xs text-red-500">{msg}</p>}
    </div>
  );
}
