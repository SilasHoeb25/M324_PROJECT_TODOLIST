import { render, screen } from '@testing-library/react'
import App from './App'

beforeEach(() => {
  global.fetch = vi.fn(() =>
    Promise.resolve({
      json: () => Promise.resolve([]),
    })
  )
})

afterEach(() => {
  vi.restoreAllMocks()
})

describe('App', () => {
  it('rendert die Überschrift', () => {
    render(<App />)
    expect(screen.getByText('ToDo Liste')).toBeInTheDocument()
  })
})