import { Disclosure } from '@headlessui/react'
import { MenuIcon, XIcon } from '@heroicons/react/outline'
import { useAuth } from '../context/auth'
import { Link } from 'react-router-dom'
import React, { useEffect, useState } from 'react'
import { useRecipe } from '../context/recipe'

export const Navbar = () => {
  const { isAuthenticated, logout } = useAuth()
  const { getRecipes } = useRecipe()

  const [query, setQuery] = useState('')

  const navigation = [
    { name: 'Dashboard', to: '/' },
    { name: 'Recipes', to: '/recipes' },
  ]

  function classNames(...classes) {
    return classes.filter(Boolean).join(' ')
  }

  const handleSearch = (e) => {
    setQuery(e.target.value)
    getRecipes(e.target.value)
  }

  // Oder aber dafür unten
  // beim Search input auf Zeile 69
  //onChange = { setquery } schreiben

  useEffect(() => {
    getRecipes()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <>
      <Disclosure as='nav' className='bg-gray-800'>
        {({ open }) => (
          <>
            <div className='max-w-6xl mx-auto px-4 sm:px-6 lg:px-8'>
              <div className='flex items-center justify-between h-16'>
                <div className='flex items-center'>
                  <div className='flex-shrink-0'>
                    {/* Hier euer Logo rein!! */}
                    <img
                      className='h-8 w-8'
                      src='https://tailwindui.com/img/logos/workflow-mark-indigo-500.svg'
                      alt='Workflow'
                    />
                  </div>
                  <div className='hidden md:block'>
                    <div className='ml-10 flex items-baseline space-x-4'>
                      {navigation.map((item) => (
                        <Link
                          key={item.name}
                          to={item.to}
                          className={classNames(
                            window.location.pathname === item.to
                              ? 'bg-gray-900 text-white'
                              : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                            'px-3 py-2 rounded-md text-sm font-medium'
                          )}>
                          {item.name}
                        </Link>
                      ))}
                      <input
                        type='search'
                        onChange={handleSearch}
                        value={query}
                        placeholder='Search for recipes'
                        className='w-full rounded-lg border p-2 focus:border-blue-500 focus:bg-white focus:outline-none'
                      />
                    </div>
                  </div>
                </div>
                <div className='hidden md:block'>
                  <div className='ml-4 flex items-center md:ml-6'>
                    {isAuthenticated ? (
                      <Link
                        to='/'
                        onClick={() => logout()}
                        className='bg-gray-800 p-1 rounded-full text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white'>
                        <span className='sr-only'>Logout</span>
                        Logout
                      </Link>
                    ) : (
                      <Link
                        to='login'
                        className='bg-gray-800 p-1 rounded-full text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white'>
                        <span className='sr-only'>Login</span>
                        Login
                      </Link>
                    )}
                  </div>
                </div>
                <div className='-mr-2 flex md:hidden'>
                  {/* Mobile menu button */}
                  <Disclosure.Button className='bg-gray-800 inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white'>
                    <span className='sr-only'>Open main menu</span>
                    {open ? (
                      <XIcon className='block h-6 w-6' aria-hidden='true' />
                    ) : (
                      <MenuIcon className='block h-6 w-6' aria-hidden='true' />
                    )}
                  </Disclosure.Button>
                </div>
              </div>
            </div>

            <Disclosure.Panel className='md:hidden'>
              <div className='px-2 pt-2 pb-3 space-y-1 sm:px-3'>
                {navigation.map((item) => (
                  <Disclosure.Button
                    key={item.name}
                    as={Link}
                    to={item.to}
                    className={classNames(
                      item.current
                        ? 'bg-gray-900 text-white'
                        : 'text-gray-300 hover:bg-gray-700 hover:text-white',
                      'block px-3 py-2 rounded-md text-base font-medium'
                    )}
                    aria-current={item.current ? 'page' : undefined}>
                    {item.name}
                  </Disclosure.Button>
                ))}
              </div>
              {isAuthenticated ? (
                <Disclosure.Button
                  key='Logout'
                  as={Link}
                  to='/'
                  onClick={() => logout()}
                  className='block mt-3 px-3 py-2 rounded-md text-base font-medium text-gray-400 hover:text-white hover:bg-gray-700'>
                  Logout
                </Disclosure.Button>
              ) : (
                <Disclosure.Button
                  key='Login'
                  as={Link}
                  to='login'
                  className='block mt-3 px-3 py-2 rounded-md text-base font-medium text-gray-400 hover:text-white hover:bg-gray-700'>
                  Login
                </Disclosure.Button>
              )}
            </Disclosure.Panel>
          </>
        )}
      </Disclosure>
    </>
  )
}
