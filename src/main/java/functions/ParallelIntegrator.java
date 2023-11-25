package functions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelIntegrator
{
//    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors(); // количество доступных процессоров
    private static final int NUM_THREADS = 4; // просто 4
    private static final double AREA_PRECISION = 0.001; // точность вычисления интеграла

    public double integrate(Integrable function) throws InterruptedException, ExecutionException
    {
        // получение границ интегрирования
        double lowerBound = function.getLeftBound();
        double upperBound = function.getRightBound();

        // создание пула потоков для параллельных вычислений
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS); // создаем ExecutorService с фиксированным числом потоков
        List<Future<Double>> futures = new ArrayList<>(); // инициализируем список futures для хранения результатов выполнения каждого потока

        // разделение задач на подзадачи и отправка их на выполнение
        double intervalSize = (upperBound - lowerBound) / NUM_THREADS; // разбиваем общий интервал интегрирования на подынтервалы
        for (int i = 0; i < NUM_THREADS; i++) // для каждого из них запускаем вычисление частичного интеграла в отдельном потоке
        {
            double start = lowerBound + i * intervalSize;
            double end = start + intervalSize;
            futures.add(executorService.submit(() -> computePartialIntegral(function, start, end))); // результаты добавляем в список futures
        }

        // остановка пула потоков и ожидание завершения всех задач
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // суммирование результатов
        double totalIntegral = 0.0;
        for (Future<Double> future : futures)
        {
            totalIntegral += future.get();
        }

        return totalIntegral;
    }

    private double computePartialIntegral(Integrable function, double start, double end)
    {
        double intervalSize = end - start;
        double sum = 0.0;

        for (double x = start; x < end; x += AREA_PRECISION)
        {
            double y1 = function.getValueX(x);
            double y2 = function.getValueX(x + AREA_PRECISION);
            double averageY = (y1 + y2) / 2.0;
            double area = averageY * AREA_PRECISION;
            sum += area;
        }

        return sum * intervalSize;
    }
}