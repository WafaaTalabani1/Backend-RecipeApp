import { useContext, createContext, useState, useMemo } from 'react'

const AuthContext = createContext()

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setisAuthenticated] = useState(!!sessionStorage.getItem('user'))
  const [user, setUser] = useState(sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user')) : null);
  const [error, setError] = useState('')
  const [isLoading, setIsLoading] = useState(false)

  const login = (username, password) => {
    setIsLoading(true)
    try {
      fetch('http://localhost:8099/api/v1/login', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({ username, password })
      })
        .then((res) => {
          if (res.status >= 400) {
            throw new Error();
          } else {
            return res.json()
          }
        })
        .then((data) => {
          setUser(data)
          sessionStorage.setItem('user', JSON.stringify(data))
          setisAuthenticated(true)
        })
    } catch (err) {
      console.error(err)
      setisAuthenticated(false)
    } finally {
      setIsLoading(false)
    }
  }

  const register = (firstName, lastName, email, password) => {
    fetch('http://localhost:8099/api/v1/registration', {
      body: JSON.stringify({firstName, lastName, email, password }),
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
    })
        .then(() => {
        })
        .catch(err => console.error(err));
  }

  const logout = () => {
    setisAuthenticated(false)
    sessionStorage.removeItem('user')
  }

  const value = useMemo(
    () => ({
      isAuthenticated,
      user,
      error,
      setError,
      isLoading,
      login,
      register,
      logout,
    }),
    [isAuthenticated, user, error, isLoading]
  )

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)
