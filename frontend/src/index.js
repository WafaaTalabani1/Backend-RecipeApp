import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import './index.css'
import { AuthProvider } from './context/auth'
import { RecipeProvider } from './context/recipe'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AuthProvider>
      <RecipeProvider>
        <Router>
          <Routes>
            <Route path='/*' element={<App />} />
          </Routes>
        </Router>
      </RecipeProvider>
    </AuthProvider>
  </React.StrictMode>
)
