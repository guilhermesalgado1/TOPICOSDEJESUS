// src/app/amostras/novo/page.jsx
'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import {
  getPropriedades,
  getTiposSolo,
  createAmostra,        // POST /amostras  { propriedadeId, tipoSoloId }
} from '@/services/apiAmostra';

export default function NovaAmostra() {
  const router = useRouter();

  /* opções para os selects */
  const [props, setProps] = useState([]);
  const [solos, setSolos] = useState([]);

  /* valores escolhidos */
  const [propriedadeId, setPropId] = useState('');
  const [tipoSoloId,    setSoloId] = useState('');

  /* estados de feedback */
  const [saving, setSaving] = useState(false);
  const [error,  setError]  = useState(null);

  /* carrega listas em paralelo */
  useEffect(() => {
    Promise.all([getPropriedades(), getTiposSolo()])
      .then(([p, t]) => {
        setProps(p.data);
        setSolos(t.data);
      })
      .catch(err => setError(err.message));
  }, []);

  /* submit */
  async function handleSubmit(e) {
    e.preventDefault();
    setSaving(true);
    setError(null);

    try {
      await createAmostra({ propriedadeId, tipoSoloId });
      router.push('/amostras');          // volta para listagem
    } catch (err) {
      setError(err.response?.data?.message || err.message);
    } finally {
      setSaving(false);
    }
  }

  return (
    <div className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-6">Nova amostra</h1>

      {/* mensagem de erro */}
      {error && <p className="mb-4 text-red-500">{error}</p>}

      <form onSubmit={handleSubmit} className="space-y-4">
        {/* select propriedade */}
        <div>
          <label className="block mb-1 text-sm">Propriedade</label>
          <select
            value={propriedadeId}
            onChange={e => setPropId(e.target.value)}
            required
            className="w-full bg-gray-800 px-3 py-2 rounded"
          >
            <option value="" disabled>Selecione…</option>
            {props.map(p => (
              <option key={p.id} value={p.id}>{p.nome}</option>
            ))}
          </select>
        </div>

        {/* select tipo de solo */}
        <div>
          <label className="block mb-1 text-sm">Tipo de solo</label>
          <select
            value={tipoSoloId}
            onChange={e => setSoloId(e.target.value)}
            required
            className="w-full bg-gray-800 px-3 py-2 rounded"
          >
            <option value="" disabled>Selecione…</option>
            {solos.map(s => (
              <option key={s.id} value={s.id}>{s.nome}</option>
            ))}
          </select>
        </div>

        {/* botões */}
        <div className="flex items-center gap-4 pt-2">
          <button
            type="submit"
            disabled={saving}
            className="px-4 py-2 bg-green-600 hover:bg-green-700 rounded disabled:opacity-50"
          >
            {saving ? 'Salvando…' : 'Salvar'}
          </button>

          <button
            type="button"
            onClick={() => router.back()}
            className="text-sm hover:underline"
          >
            Cancelar
          </button>
        </div>
      </form>
    </div>
  );
}
