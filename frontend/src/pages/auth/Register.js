import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/auth'
import { Navigate } from 'react-router-dom'
import { useState } from 'react'

const Register = () => {
  const { isAuthenticated, register, error } = useAuth()

  const [firstName, setFirstName] = useState('')
  const [pwd, setPwd] = useState('')
  const [lastName, setLastName] = useState('')
  const [email, setEmail] = useState('')
  const navigate = useNavigate();

  if (isAuthenticated) return <Navigate to='/' replace />

  const handleRegister = () => {
    try {
      register(firstName, lastName, email, pwd)
      navigate('/login')
    } catch (err) {
      console.error(err);
    }
  }

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
              Create a new account
            </h1>

            <form className='mt-6'>
              {error && <span className='text-red-500 my-3'>{error}</span>}

              <div className='my-3'>
                <label className='block text-gray-700'>First name</label>
                <input
                    type='text'
                    name='firstName'
                    required
                    onChange={(e) => setFirstName(e.target.value)}
                    placeholder='Enter first name'
                    className='mt-2 w-full rounded-lg border px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none'
                    autoComplete='off'
                />
              </div>

              <div className='my-3'>
                <label className='block text-gray-700'>Last name</label>
                <input
                    type='text'
                    name='lastName'
                    required
                    onChange={(e) => setLastName(e.target.value)}
                    placeholder='Enter last name'
                    className='mt-2 w-full rounded-lg border px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none'
                    autoComplete='off'
                />
              </div>

              <div className='my-3'>
                <label className='block text-gray-700'>Email</label>
                <input
                  type='email'
                  name='email'
                  required
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder='Enter Email'
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
                  onChange={(e) => setPwd(e.target.value)}
                  placeholder='Enter Password'
                  autoComplete='off'
                  className='mt-2 w-full rounded-lg border px-4 py-3 focus:border-blue-500 focus:bg-white focus:outline-none'
                />
              </div>

              <button
                type='button'
                onClick={handleRegister}
                className='block w-full rounded-lg bg-indigo-500 px-3 py-3 font-semibold text-white hover:bg-indigo-700 focus:bg-indigo-700'>
                Register
              </button>
            </form>
            <hr className='my-6 w-full border-gray-300' />
            <div className='text-center'>
              <p className='mt-8'>Already an Account!</p>
              <Link
                to='/login'
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

export default Register
