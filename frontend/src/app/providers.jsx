'use client';                 // ← agora é o único componente client

import { AuthProvider } from '@/context/AuthContext';
import '../services/amplifyConfig';

export default function Providers({ children }) {
  return <AuthProvider>{children}</AuthProvider>;
}
