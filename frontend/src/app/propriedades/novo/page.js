'use client';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { createPropriedade } from '@/services/apiPropriedades';

export default function NovaPropriedade() {
  const [nome , setNome ]   = useState('');
  const [local, setLocal]   = useState('');
  const [saving, setSave ]  = useState(false);
  const [error , setError]  = useState(null);
  const router = useRouter();

  async function salvar(e) {
    e.preventDefault();
    setSave(true); setError(null);
    try {
      await createPropriedade({ nome, localizacao: local }); // polygon pode ficar null
      router.push('/propriedades');        // volta para listagem
    } catch (err) {
      setError(err.response?.data?.message || err.message);
    } finally {
      setSave(false);
    }
  }

  return (
    <div className="p-6 max-w-md mx-auto">
      <h1 className="text-xl font-bold mb-6">Nova Propriedade</h1>

      <form onSubmit={salvar} className="space-y-4">
        <input
          value={nome}
          onChange={e => setNome(e.target.value)}
          required
          className="w-full bg-gray-800 px-3 py-2 rounded"
          placeholder="Nome"
        />
        <input
          value={local}
          onChange={e => setLocal(e.target.value)}
          className="w-full bg-gray-800 px-3 py-2 rounded"
          placeholder="Localização (opcional)"
        />

        {error && <p className="text-red-500">{error}</p>}

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
