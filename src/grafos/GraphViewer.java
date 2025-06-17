
package src.grafos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GraphViewer<E> extends JFrame {

    private GraphLink<E> grafo;
    private Map<E, Point> posiciones;
    private GraphPanel panel;
    private Timer timer;

    public GraphViewer(GraphLink<E> grafo) {
        this.grafo = grafo;
        this.posiciones = new HashMap<>();

        setTitle("Visualizador de Grafo - Almacén");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel personalizado
        panel = new GraphPanel();
        add(panel);

        // Configurar timer para actualización automática
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarGrafo();
            }
        });

        // Calcular posiciones iniciales
        calcularPosiciones();

        // Botones de control
        JPanel controlPanel = new JPanel();
        JButton refreshButton = new JButton("Actualizar");
        JButton autoRefreshButton = new JButton("Auto-Actualizar");

        refreshButton.addActionListener(e -> {
            calcularPosiciones();
            repaint();
        });

        autoRefreshButton.addActionListener(e -> {
            if (timer.isRunning()) {
                timer.stop();
                autoRefreshButton.setText("Auto-Actualizar");
            } else {
                timer.start();
                autoRefreshButton.setText("Detener Auto");
            }
        });

        controlPanel.add(refreshButton);
        controlPanel.add(autoRefreshButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            dibujarGrafo(g2d);
        }
    }

    private void calcularPosiciones() {
        if (grafo.listVertex.size() == 0) return;

        posiciones.clear();
        int numNodos = grafo.listVertex.size();

        // Distribución en círculo para mejor visualización
        double angulo = 2 * Math.PI / numNodos;
        int radio = Math.min(getWidth(), getHeight()) / 3;
        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        for (int i = 0; i < numNodos; i++) {
            E nodo = grafo.listVertex.get(i).getData();

            int x = (int) (centroX + radio * Math.cos(i * angulo));
            int y = (int) (centroY + radio * Math.sin(i * angulo));

            posiciones.put(nodo, new Point(x, y));
        }
    }

    private void dibujarGrafo(Graphics2D g2d) {
        if (grafo.listVertex.size() == 0) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("No hay vértices en el grafo", 50, 50);
            return;
        }

        // Dibujar aristas
        dibujarAristas(g2d);

        // Dibujar vértices
        dibujarVertices(g2d);

        // Dibujar información del grafo
        dibujarInformacion(g2d);
    }

    private void dibujarAristas(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < grafo.listVertex.size(); i++) {
            Vertex<E> vertice = grafo.listVertex.get(i);
            E origen = vertice.getData();
            Point posOrigen = posiciones.get(origen);

            if (posOrigen == null) continue;

            for (int j = 0; j < vertice.listAdj.size(); j++) {
                Edge<E> arista = vertice.listAdj.get(j);
                E destino = arista.getRefDest().getData();
                Point posDestino = posiciones.get(destino);

                if (posDestino == null) continue;

                // Color de la arista
                g2d.setColor(Color.GRAY);

                // Dibujar flecha para grafos dirigidos
                if (grafo.directed) {
                    dibujarFlecha(g2d, posOrigen, posDestino);
                } else {
                    g2d.drawLine(posOrigen.x, posOrigen.y, posDestino.x, posDestino.y);
                }

                // Dibujar peso si existe
                if (arista.weight > -1) {
                    int midX = (posOrigen.x + posDestino.x) / 2;
                    int midY = (posOrigen.y + posDestino.y) / 2;

                    g2d.setColor(Color.RED);
                    g2d.fillOval(midX - 12, midY - 8, 24, 16);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(String.valueOf(arista.weight), midX - 5, midY + 3);
                }
            }
        }
    }

    private void dibujarFlecha(Graphics2D g2d, Point origen, Point destino) {
        // Calcular vector de dirección
        double dx = destino.x - origen.x;
        double dy = destino.y - origen.y;
        double longitud = Math.sqrt(dx * dx + dy * dy);

        // Normalizar
        dx /= longitud;
        dy /= longitud;

        // Punto donde termina la línea (cerca del borde del círculo del destino)
        int finX = (int) (destino.x - dx * 25);
        int finY = (int) (destino.y - dy * 25);

        // Dibujar línea
        g2d.drawLine(origen.x, origen.y, finX, finY);

        // Dibujar punta de flecha
        double anguloFlecha = Math.atan2(dy, dx);
        int[] xPoints = {
                finX,
                (int) (finX - 10 * Math.cos(anguloFlecha - Math.PI / 6)),
                (int) (finX - 10 * Math.cos(anguloFlecha + Math.PI / 6))
        };
        int[] yPoints = {
                finY,
                (int) (finY - 10 * Math.sin(anguloFlecha - Math.PI / 6)),
                (int) (finY - 10 * Math.sin(anguloFlecha + Math.PI / 6))
        };

        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void dibujarVertices(Graphics2D g2d) {
        for (E nodo : posiciones.keySet()) {
            Point pos = posiciones.get(nodo);

            // Determinar color según el tipo de nodo
            Color colorNodo = determinarColorNodo(nodo);

            // Dibujar círculo del vértice
            g2d.setColor(colorNodo);
            g2d.fillOval(pos.x - 25, pos.y - 25, 50, 50);

            // Borde del círculo
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(pos.x - 25, pos.y - 25, 50, 50);

            // Texto del nodo
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 10));
            String texto = nodo.toString();

            // Centrar texto
            FontMetrics fm = g2d.getFontMetrics();
            int textoWidth = fm.stringWidth(texto);
            int textoHeight = fm.getHeight();

            if (texto.length() > 8) {
                // Si el texto es muy largo, dividirlo en líneas
                String[] palabras = texto.split(" ");
                if (palabras.length >= 2) {
                    g2d.drawString(palabras[0], pos.x - fm.stringWidth(palabras[0])/2, pos.y - 5);
                    g2d.drawString(palabras[1], pos.x - fm.stringWidth(palabras[1])/2, pos.y + 10);
                } else {
                    g2d.drawString(texto.substring(0, 8) + "...", pos.x - textoWidth/2, pos.y + 3);
                }
            } else {
                g2d.drawString(texto, pos.x - textoWidth/2, pos.y + 3);
            }
        }
    }

    private Color determinarColorNodo(E nodo) {
        String nombreNodo = nodo.toString().toLowerCase();

        if (nombreNodo.contains("estantería")) {
            return new Color(70, 130, 180); // Steel Blue
        } else if (nombreNodo.contains("pasillo")) {
            return new Color(60, 179, 113); // Medium Sea Green
        } else if (nombreNodo.contains("zona") || nombreNodo.contains("carga")) {
            return new Color(220, 20, 60); // Crimson
        } else {
            return new Color(128, 128, 128); // Gray
        }
    }

    private void dibujarInformacion(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        int y = 20;
        g2d.drawString("Vértices: " + grafo.listVertex.size(), 10, y);
        y += 20;

        int totalAristas = 0;
        for (int i = 0; i < grafo.listVertex.size(); i++) {
            totalAristas += grafo.listVertex.get(i).listAdj.size();
        }
        g2d.drawString("Aristas: " + totalAristas, 10, y);
        y += 20;

        g2d.drawString("Tipo: " + (grafo.directed ? "Dirigido" : "No dirigido"), 10, y);
        y += 20;

        boolean esConexo = grafo.isConexo();
        g2d.drawString("Conectividad: " + (esConexo ? "Conexo" : "No conexo"), 10, y);

        // Leyenda de colores
        y += 40;
        g2d.drawString("Leyenda:", 10, y);
        y += 20;

        // Estantería
        g2d.setColor(new Color(70, 130, 180));
        g2d.fillOval(10, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Estantería", 30, y);
        y += 20;

        // Pasillo
        g2d.setColor(new Color(60, 179, 113));
        g2d.fillOval(10, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Pasillo", 30, y);
        y += 20;

        // Zona de carga
        g2d.setColor(new Color(220, 20, 60));
        g2d.fillOval(10, y - 10, 15, 15);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Zona de Carga", 30, y);
    }

    private void actualizarGrafo() {
        calcularPosiciones();
        repaint();
    }

    public void mostrar() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            calcularPosiciones();
            repaint();
        });
    }

    public void cerrar() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        dispose();
    }

    // Método estático para facilitar el uso
    public static <E> GraphViewer<E> visualizar(GraphLink<E> grafo) {
        GraphViewer<E> viewer = new GraphViewer<>(grafo);
        viewer.mostrar();
        return viewer;
    }
}
