'use client';               // porque usamos hooks do React

import { useRouter, useSearchParams } from 'next/navigation';
import { useContext, useEffect } from 'react';
import { AuthContext } from '@/context/AuthContext';

export default function Home() {
  const { user, login } = useContext(AuthContext);
  const router          = useRouter();
  const params          = useSearchParams();

  useEffect(() => {
    if (user) {
      // devolve para rota de origem OU dashboard
      const to = params.get('callback') || '/dashboard';
      router.replace(to);
    }
  }, [user, params, router]);

  // usuário ainda não autenticado → mostra botão Entrar
  if (!user) {
    return (
      <main className="flex h-screen items-center justify-center">
        <div className="flex flex-col items-center gap-6">
          <h1 className="text-2xl font-bold">Faça seu Login!</h1>
          <br></br>
          <button
            onClick={login}
            className="px-6 py-3 bg-blue-600 text-white rounded"
          >
            Entrar
          </button>
        </div>
      </main>
    );
  }

  return null;  // já redirecionou
}
