import React from 'react'
import { useAuth } from '../context/auth'
import { Outlet, Navigate } from 'react-router-dom'

export const PrivateRoute = () => {
  const { isAuthenticated } = useAuth()

  return isAuthenticated ? <Outlet /> : <Navigate to='/login' replace />
}
