import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class BruteCollinearPoints {
    private final ArrayList<LineSegment> _segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] sortedPoints = new Point[points.length];
        System.arraycopy(points, 0, sortedPoints, 0, sortedPoints.length);

        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        int number = sortedPoints.length;
        for (int dot0 = 0; dot0 < number - 3; dot0++) {
            for (int dot1 = dot0 + 1; dot1 < number - 2; dot1++) {
                double s12 = sortedPoints[dot0].slopeTo(sortedPoints[dot1]);
                for (int dot2 = dot1 + 1; dot2 < number - 1; dot2++) {
                    double s23 = sortedPoints[dot1].slopeTo(sortedPoints[dot2]);
                    if (Double.compare(s12, s23) != 0) {
                        continue;
                    }
                    for (int dot3 = dot2 + 1; dot3 < number; dot3++) {
                        double s34 = sortedPoints[dot2].slopeTo(sortedPoints[dot3]);
                        if (s23 != s34) {
                            continue;
                        }

                        _segments.add(new LineSegment(sortedPoints[dot0], sortedPoints[dot3]));
                    }
                }
            }
        }


    }

    public int numberOfSegments() {
        return _segments.size();
    }

    public LineSegment[] segments() {
        return _segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();

            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
