'use client';                     // necessário porque usa estado/logout
import Link from 'next/link';
import { useContext } from 'react';
import { AuthContext } from '@/context/AuthContext';

export default function NavBar() {
  const { user, logout } = useContext(AuthContext);

  return (
    <nav className="bg-gray-900 text-white px-4 py-2 flex items-center">
      {/* logo / home */}
      <Link href="/dashboard" className="font-bold mr-6">SoloApp</Link>

      {/* links principais */}
      <Link href="/amostras"  className="mr-4 hover:underline">Amostras</Link>
      <Link href="/analises"  className="mr-4 hover:underline">Análises</Link>
      <Link href="/pessoas"   className="mr-auto hover:underline">Pessoas</Link>

      {/* avatar + logout */}
      <button onClick={logout} className="text-sm hover:underline">
        {user?.username ?? 'User'} ▼
      </button>
    </nav>
  );
}
