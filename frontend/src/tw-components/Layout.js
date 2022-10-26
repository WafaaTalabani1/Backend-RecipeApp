import React from 'react'
import { Outlet, useLocation } from 'react-router-dom'
import { Navbar } from './Navbar'

const Layout = () => {
  const location = useLocation()
  const showNavBar =
    location.pathname !== '/login' ||
    location.pathname !== '/login/' ||
    location.pathname !== '/register' ||
    location.pathname !== '/register/'

  return (
    <div className='w-full h-full mx-auto'>
      {showNavBar && <Navbar />}
      <Outlet />
    </div>
  )
}
export default Layout
