import { Link } from 'react-router-dom'
import { useAuth } from '../../context/auth'
import { Navigate } from 'react-router-dom'
import { useState } from 'react'

const Login = () => {
  const { isAuthenticated, login, error } = useAuth()

  const [username, setUsername] = useState('')
  const [pwd, setPwd] = useState('')

  if (isAuthenticated) {
    console.log("IS AUTHENTICATED")
    return <Navigate to='/' replace />
  }


  const handleLogin = () => login(username, pwd)

  return (
    <>
      <section className='flex h-screen flex-col items-center justify-center md:flex-row'>
        <div className='hidden h-screen w-full bg-indigo-600 md:w-1/2 lg:block xl:w-2/3'>
          <img
            src='https://source.unsplash.com/random'
            alt=''
            className='h-full w-full object-cover'
          />
        </div>

        <div className='flex h-screen w-full flex-col items-center justify-center bg-white px-6 md:mx-0 md:w-1/2 md:max-w-md lg:max-w-full lg:px-16 xl:w-1/3 xl:px-12'>
          <div className='h-100 w-full'>
            <div className='mx-auto h-40 w-40'>
              <img
                src='https://tailwindui.com/img/logos/workflow-mark-indigo-500.svg'
                alt='Workflow'
              />
            </div>
            <h1 className='mt-12 text-xl font-bold leading-tight md:text-2xl'>
              Log in to your account
            </h1>

            <form className='mt-6'>
              {error && <span className='text-red-500 my-3'>{error}</span>}

              <div className='my-3'>
                <label className='block text-gray-700'>Username</label>
                <input
                  type='text'
                  name='username'
                  required
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                  placeholder='Enter Username'
                  className='mt-2 w-full rounded-lg border px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none'
                  autoComplete='off'
                />
              </div>

              <div className='my-3'>
                <label className='block text-gray-700'>Password</label>

                <input
                  type='password'
                  required
                  minLength={6}
                  maxLength={20}
                  name='password'
                  value={pwd}
                  onChange={(e) => setPwd(e.target.value)}
                  placeholder='Enter Password'
                  autoComplete='off'
                  className='mt-2 w-full rounded-lg border px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none'
                />
              </div>

              <button
                type='button'
                onClick={handleLogin}
                className='block w-full rounded-lg bg-indigo-500 px-3 py-3 font-semibold text-white hover:bg-indigo-700 focus:bg-indigo-700'>
                Log In
              </button>
            </form>
            <hr className='my-6 w-full border-gray-300' />
            <div className='text-center'>
              <p className='mt-8'>Need an account?</p>
              <Link
                to='/register'
                className='font-semibold text-blue-500 hover:text-blue-700'>
                Create an account
              </Link>
            </div>
          </div>
        </div>
      </section>
    </>
  )
}

export default Login
