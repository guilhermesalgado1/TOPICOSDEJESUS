import { createContext, useState, useEffect } from 'react';
import { Auth } from 'aws-amplify';

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Verifica se já existe um usuário autenticado ao carregar o app
  useEffect(() => {
    Auth.currentAuthenticatedUser()
      .then(cognitoUser => {
        setUser(cognitoUser);
      })
      .catch(() => {
        setUser(null);
      })
      .finally(() => setLoading(false));
  }, []);

  const login = () => Auth.federatedSignIn();

  const logout = async () => {
    await Auth.signOut();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
