'use client';
import { useEffect, useState } from 'react';
import { useRouter, useParams } from 'next/navigation';
import { getTipoSolo, updateTipoSolo } from '@/services/apiTiposSolo';

export default function EditTipoSolo() {
  const { id }      = useParams();
  const router      = useRouter();
  const [nome,  setNome ] = useState('');
  const [desc,  setDesc ] = useState('');
  const [load,  setLoad ] = useState(true);
  const [sav ,  setSav  ] = useState(false);
  const [err ,  setErr  ] = useState(null);

  useEffect(() => {
    getTipoSolo(id)
      .then(r => { setNome(r.data.nome); setDesc(r.data.descricao ?? ''); })
      .catch(e => setErr(e.message))
      .finally(() => setLoad(false));
  }, [id]);

  async function salvar(e) {
    e.preventDefault();
    setSav(true); setErr(null);
    try {
      await updateTipoSolo(id, { nome, descricao: desc });
      router.push('/tiposSolo');
    } catch (e) {
      setErr(e.response?.data?.message || e.message);
    } finally {
      setSav(false);
    }
  }

  if (load)  return <p className="p-6">Carregando…</p>;
  if (err && !sav) return <p className="p-6 text-red-500">{err}</p>;

  return (
    <div className="p-6 max-w-md mx-auto">
      <h1 className="text-xl font-bold mb-6">Editar Tipo de Solo #{id}</h1>

      <form onSubmit={salvar} className="space-y-4">
        <input
          className="w-full bg-gray-800 px-3 py-2 rounded"
          value={nome}
          onChange={e => setNome(e.target.value)}
          required
        />
        <textarea
          className="w-full bg-gray-800 px-3 py-2 rounded"
          value={desc}
          onChange={e => setDesc(e.target.value)}
          rows={3}
        />
        {err && <p className="text-red-500">{err}</p>}
        <button
          type="submit"
          disabled={sav}
          className="px-4 py-2 bg-green-600 hover:bg-green-700 rounded disabled:opacity-50"
        >
          {sav ? 'Salvando…' : 'Salvar'}
        </button>
      </form>
    </div>
  );
}
