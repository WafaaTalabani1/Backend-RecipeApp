import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Login from './pages/auth/Login'
import Register from './pages/auth/Register'
import { PublicRoute } from './routes/PublicRoute'
import { PrivateRoute } from './routes/PrivateRoute'
import Search from './tw-components/Search'
import Pages from './pages/Pages'
import Category from './tw-components/Category'
import Layout from './tw-components/Layout'
import Home from './pages/Home'
import Error404 from './pages/error/Error404'
import Cuisine from './pages/Cuisine';
import Searched from './pages/Searched';
import Recipe from './pages/Recipe';
import Recipes from './tw-components/Recipes';


const App = () => {
  return (
    <Routes>
      <Route path='/' element={<Layout />}>
        <Route index element={<Home />} />
        <Route element={<PrivateRoute />}></Route>
        <Route element={<PublicRoute />}>
          <Route path='search' element={<Search />} />
          <Route path='category' element={<Category />} />
          <Route path= "/cuisine/:type" element={<Cuisine />} />
          <Route path="/searched/:search" element={<Searched />} />
          <Route path="/recipes" element={<Recipes />} />
          <Route path="/recipe/:name" element={<Recipe />} />
        </Route>
        <Route element={<PublicRoute restricted />}>
          <Route path='login' element={<Login />} />
          <Route path='register' element={<Register />} />
        </Route>
        <Route path='*' element={<Error404 />} />
      </Route>
    </Routes>
  )
}

export default App
