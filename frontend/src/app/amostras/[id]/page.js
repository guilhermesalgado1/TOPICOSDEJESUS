'use client';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { getAmostra, updateAmostra, getPropriedades, getTiposSolo }
        from '@/services/apiAmostra';

export default function AmostraDetalhe({ params }) {
  const id = params.id;             // vem da rota [id]
  const router = useRouter();

  const [amostra, setAmostra] = useState(null);
  const [props,   setProps]   = useState([]);
  const [tipos,   setTipos]   = useState([]);
  const [err,     setErr]     = useState(null);
  const [saving,  setSaving]  = useState(false);

  // carrega tudo ao montar
  useEffect(() => {
    Promise.all([
      getAmostra(id),
      getPropriedades(),
      getTiposSolo()
    ])
      .then(([a, p, t]) => {
        setAmostra(a.data);
        setProps(p.data);
        setTipos(t.data);
      })
      .catch(e => setErr(e.message));
  }, [id]);

  if (err)        return <p className="p-6 text-red-500">{err}</p>;
  if (!amostra)   return <p className="p-6">Carregando…</p>;

  // handlers de edição
  const handleChange = e =>
    setAmostra({ ...amostra, [e.target.name]: e.target.value });

  const handleSave = () => {
    setSaving(true);
    updateAmostra(id, amostra)
      .then(() => router.push('/amostras'))   // volta para lista
      .catch(e => { setErr(e.message); setSaving(false); });
  };

  return (
    <div className="p-6 space-y-4">
      <h1 className="text-2xl font-bold">Editar Amostra #{id}</h1>

      {/* Propriedade */}
      <label className="block">
        <span className="block text-sm">Propriedade</span>
        <select
          name="propriedadeId"
          value={amostra.propriedadeId ?? ''}
          onChange={handleChange}
          className="bg-gray-800 p-2 rounded w-60"
        >
          <option value="">-- selecione --</option>
          {props.map(p => (
            <option key={p.id} value={p.id}>{p.nome}</option>
          ))}
        </select>
      </label>

      {/* Tipo de solo */}
      <label className="block">
        <span className="block text-sm">Tipo de Solo</span>
        <select
          name="tipoSoloId"
          value={amostra.tipoSoloId ?? ''}
          onChange={handleChange}
          className="bg-gray-800 p-2 rounded w-60"
        >
          <option value="">-- selecione --</option>
          {tipos.map(t => (
            <option key={t.id} value={t.id}>{t.nome}</option>
          ))}
        </select>
      </label>

      {/* Botão salvar */}
      <button
        onClick={handleSave}
        disabled={saving}
        className="px-5 py-2 bg-blue-600 rounded hover:bg-blue-500 disabled:opacity-50"
      >
        {saving ? 'Salvando…' : 'Salvar'}
      </button>
    </div>
  );
}
