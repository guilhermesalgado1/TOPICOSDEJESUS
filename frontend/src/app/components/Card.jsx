'use client';
import Link from 'next/link';

/**
 * Card genérico para mostrar a última entidade criada
 * ou um call-to-action para criar caso não exista.
 */
export default function Card({ title, item, entity }) {
  return (
    <div className="rounded-lg bg-gray-800 p-4 shadow-md flex flex-col">
      {/* título */}
      <h2 className="text-lg font-semibold mb-2">{title}</h2>

      {/* conteúdo */}
      {item ? (
        <>
          <p className="text-4xl font-bold text-green-400 mb-1">{item.id}</p>
          <p className="text-sm text-gray-300 truncate">
            {/* tenta exibir nome/descrição, se houver */}
            {item.nome || item.descricao || '—'}
          </p>

          <Link
            href={`/${entity}/${item.id}`}
            className="mt-auto inline-block text-sm text-blue-400 hover:underline"
          >
            Ver detalhes →
          </Link>
        </>
      ) : (
        <>
          <p className="text-sm text-gray-400 mb-4">
            Nenhum registro encontrado.
          </p>
          <Link
            href={`/${entity}/novo`}
            className="mt-auto inline-block rounded bg-blue-600 px-3 py-1 text-sm hover:bg-blue-700"
          >
            Criar primeiro
          </Link>
        </>
      )}
    </div>
  );
}
