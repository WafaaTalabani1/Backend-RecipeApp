import React from 'react'
import { useAuth } from '../context/auth'
import { Outlet, Navigate } from 'react-router-dom'

export const PublicRoute = ({ restricted }) => {
  const { isAuthenticated } = useAuth()

  return isAuthenticated && restricted ? <Navigate to='/' replace /> : <Outlet />
}
