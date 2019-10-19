import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class FastCollinearPoints {
    private final ArrayList<LineSegment> _segments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
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

        for (Point originPoint : sortedPoints) {
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, originPoint.slopeOrder());

            for (int j = 1; j < sortedPoints.length;) {
                ArrayList<Point> candidates = new ArrayList<>();
                double slopScore = originPoint.slopeTo(pointsBySlope[j]);

                do {
                    candidates.add(pointsBySlope[j++]);
                } while (j < sortedPoints.length && Double.compare(originPoint.slopeTo(pointsBySlope[j]), slopScore) == 0);

                if (candidates.size() > 2 && originPoint.compareTo(candidates.get(0)) < 0) {
                    _segments.add(new LineSegment(originPoint, candidates.get(candidates.size() - 1)));
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
        // In in = new In(args[0]);
        In in = new In("data/input6.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
