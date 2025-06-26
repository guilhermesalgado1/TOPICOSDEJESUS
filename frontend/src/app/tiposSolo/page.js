'use client';
import { useEffect, useState } from 'react';
import Link from 'next/link';
import { getTiposSolo, deleteTipoSolo } from '@/services/apiTiposSolo';

export default function TiposSoloPage() {
  const [rows, setRows]   = useState([]);
  const [loading, setLd ] = useState(true);
  const [error , setErr ] = useState(null);

  useEffect(() => {
    getTiposSolo()
      .then(r => setRows(r.data))
      .catch(err => setErr(err.message))
      .finally(() => setLd(false));
  }, []);

  async function remover(id) {
    if (!confirm('Excluir este tipo de solo?')) return;
    await deleteTipoSolo(id);
    setRows(rows.filter(r => r.id !== id));
  }

  if (loading) return <p className="p-6">Carregando…</p>;
  if (error)   return <p className="p-6 text-red-500">{error}</p>;

  return (
    <div className="p-6">
      <div className="flex items-center justify-between mb-4">
        <h1 className="text-2xl font-bold">Tipos de Solo</h1>
        <Link
          href="/tiposSolo/novo"
          className="px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded"
        >
          + Novo
        </Link>
      </div>

      <table className="min-w-full text-sm">
        <thead>
          <tr className="border-b border-gray-700">
            <th className="text-left py-2">ID</th>
            <th className="text-left py-2">Nome</th>
            <th className="text-left py-2">Descrição</th>
            <th className="w-32"></th>
          </tr>
        </thead>
        <tbody>
          {rows.map(t => (
            <tr key={t.id} className="border-b border-gray-800">
              <td className="py-2">{t.id}</td>
              <td className="py-2">{t.nome}</td>
              <td className="py-2">{t.descricao ?? '-'}</td>
              <td className="py-2 flex gap-2">
                <Link
                  href={`/tiposSolo/${t.id}`}
                  className="text-blue-400 hover:underline"
                >
                  Ver
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
