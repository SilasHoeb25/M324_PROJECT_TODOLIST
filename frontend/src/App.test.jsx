import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import App from './App'

beforeEach(() => {
  global.fetch = vi.fn()
})

afterEach(() => {
  vi.restoreAllMocks()
})

it('zeigt geladene Aufgaben korrekt an', async () => {
  global.fetch.mockResolvedValueOnce({
    json: () => Promise.resolve([
      { taskdescription: 'Einkaufen' },
      { taskdescription: 'Sport machen' }
    ])
  })

  render(<App />)

  await waitFor(() => {
    expect(screen.getByText(/Einkaufen/)).toBeInTheDocument()
    expect(screen.getByText(/Sport machen/)).toBeInTheDocument()
  })
})

it('zeigt die korrekte Anzahl Elemente an', async () => {
  global.fetch.mockResolvedValueOnce({
    json: () => Promise.resolve([
      { taskdescription: 'Aufgabe 1' },
      { taskdescription: 'Aufgabe 2' },
      { taskdescription: 'Aufgabe 3' }
    ])
  })

  render(<App />)

  await waitFor(() => {
    const items = screen.getAllByRole('listitem')
    expect(items).toHaveLength(3)
  })
})

it('sendet neues Element beim Absenden des Formulars', async () => {
  global.fetch.mockResolvedValueOnce({
    json: () => Promise.resolve([])
  })
  global.fetch.mockResolvedValueOnce({})

  render(<App />)

  const input = screen.getByRole('textbox')
  const button = screen.getByText('Absenden')

  fireEvent.change(input, { target: { value: 'Neue Aufgabe' } })
  fireEvent.click(button)

  await waitFor(() => {
    expect(global.fetch).toHaveBeenCalledWith('/api/tasks', expect.objectContaining({
      method: 'POST',
      body: JSON.stringify({ taskdescription: 'Neue Aufgabe' })
    }))
  })
})