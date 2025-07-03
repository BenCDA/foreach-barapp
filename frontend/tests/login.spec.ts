import { mount } from '@vue/test-utils'
import Login from '../src/views/Login.vue'
import { vi } from 'vitest'

vi.mock('../src/services/api', () => ({
  api: { post: vi.fn() }
}))

const push = vi.fn()
vi.mock('vue-router', () => ({
  useRouter: () => ({ push })
}))

const token = 'eyJhbGciOiJub25lIn0.eyJyb2xlIjoiUk9MRV9DTElFTlQifQ.'

describe('Login.vue', () => {
  beforeEach(() => {
    localStorage.clear()
  })

  it('stores token and redirects on successful login', async () => {
    const { api } = await import('../src/services/api')
    ;(api.post as any).mockResolvedValue({ token })

    const wrapper = mount(Login)
    await wrapper.find('input[type="email"]').setValue('foo@example.com')
    await wrapper.find('input[type="password"]').setValue('bar')
    await wrapper.find('form').trigger('submit.prevent')

    expect(localStorage.getItem('jwt')).toBe(token)
    expect(localStorage.getItem('role')).toBe('ROLE_CLIENT')
    expect(push).toHaveBeenCalledWith('/')
  })
})
