'use client';
import { useState } from 'react';
import api from '@/services/api';

export default function TipoSoloButton() {
  const [lista, setLista]   = useState([]);
  const [loading, setLoad]  = useState(false);
  const [erro, setErro]     = useState(null);

  async function buscar() {
    try {
      setLoad(true);
      const { data } = await api.get('/tipos-solo');
      setLista(data);
    } catch (e) {
      setErro(e.message ?? 'Erro ao buscar');
    } finally {
      setLoad(false);
    }
  }

  return (
    <>
      <button
        onClick={buscar}
        className="mt-8 px-4 py-2 bg-emerald-600 text-white rounded"
      >
        Listar tipos de solo
      </button>

      {loading && <p className="mt-4">Carregando…</p>}
      {erro && <p className="mt-4 text-red-500">{erro}</p>}

      <ul className="mt-4 list-disc pl-6">
        {lista.map(t => (
          <li key={t.id}>{t.nome}</li>
        ))}
      </ul>
    </>
  );
}
