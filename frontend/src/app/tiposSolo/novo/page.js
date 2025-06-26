'use client';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { createTipoSolo } from '@/services/apiTiposSolo';

export default function NovoTipoSolo() {
  const [nome , setNome ] = useState('');
  const [desc , setDesc ] = useState('');
  const [saving, setSav]  = useState(false);
  const [err   , setErr ] = useState(null);
  const router = useRouter();

  async function salvar(e) {
    e.preventDefault();
    setSav(true); setErr(null);
    try {
      await createTipoSolo({ nome, descricao: desc });
      router.push('/tiposSolo');
    } catch (e) {
      setErr(e.response?.data?.message || e.message);
    } finally {
      setSav(false);
    }
  }

  return (
    <div className="p-6 max-w-md mx-auto">
      <h1 className="text-xl font-bold mb-6">Novo Tipo de Solo</h1>

      <form onSubmit={salvar} className="space-y-4">
        <input
          className="w-full bg-gray-800 px-3 py-2 rounded"
          placeholder="Nome"
          value={nome}
          onChange={e => setNome(e.target.value)}
          required
        />
        <textarea
          className="w-full bg-gray-800 px-3 py-2 rounded"
          placeholder="Descrição (opcional)"
          value={desc}
          onChange={e => setDesc(e.target.value)}
          rows={3}
        />
        {err && <p className="text-red-500">{err}</p>}
        <button
          type="submit"
          disabled={saving}
          className="px-4 py-2 bg-green-600 hover:bg-green-700 rounded disabled:opacity-50"
        >
          {saving ? 'Salvando…' : 'Criar'}
        </button>
      </form>
    </div>
  );
}
