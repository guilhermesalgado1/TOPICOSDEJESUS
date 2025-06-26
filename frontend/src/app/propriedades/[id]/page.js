'use client';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import {
  getPropriedade,
  updatePropriedade,
} from '@/services/apiPropriedades';

export default function PropriedadeDetalhe({ params }) {
  // Next 15: params é Promise – unwrap:
  const [id] = useState(() => params.id);         // uma vez só

  const router = useRouter();
  const [nome, setNome]             = useState('');
  const [local, setLocal]           = useState('');
  const [loading, setLoad]          = useState(true);
  const [saving , setSave]          = useState(false);
  const [error , setError]          = useState(null);

  // busca inicial
  useEffect(() => {
    getPropriedade(id)
      .then(r => {
        setNome(r.data.nome);
        setLocal(r.data.localizacao ?? '');
      })
      .catch(err => setError(err.message))
      .finally(() => setLoad(false));
  }, [id]);

  async function salvar(e) {
    e.preventDefault();
    setSave(true); setError(null);
    try {
      await updatePropriedade(id, { nome, localizacao: local });
      router.back();
    } catch (err) {
      setError(err.response?.data?.message || err.message);
    } finally { setSave(false); }
  }

  if (loading) return <p className="p-6">Carregando…</p>;
  if (error)   return <p className="p-6 text-red-500">{error}</p>;

  return (
    <div className="p-6 max-w-md mx-auto">
      <h1 className="text-xl font-bold mb-6">Editar Propriedade #{id}</h1>

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
          {saving ? 'Salvando…' : 'Salvar'}
        </button>
      </form>
    </div>
  );
}
