CREATE TABLE `kpi` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `measures` text NOT NULL,
  PRIMARY KEY  (`id`)
);

INSERT INTO kpi VALUES (8, 'Number of users with computations scaled up through parallelisation of code', 'Percent of CPU core hour utilisation categorised by System Threshold (where thresholds are inherent within each computing system e.g. Core, CPU, Board, Node, Partition and CPU core hour utilisation is calculated by categorising each Job as within a System Threshold');
INSERT INTO kpi VALUES (9, 'Increase in throughput of computations', 'Number of NeSI users get on average an order of magnitude scale up (measured by scale-up of any limiting factor as a power of two - is for use with any service that achieves improvement for any current limiting factor(s) of a user. Measured as a power of 2 (i.e. an order of magnitute in base 2), this can reflect a scale up to more CPUs, or it could relate to memory, input/output or other factors in order to increase the throughput of computations');

